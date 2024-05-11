package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.dto.VisitRequest;
import com.bukup.vetclinic.dto.VisitScheduleSegment;
import com.bukup.vetclinic.model.*;
import com.bukup.vetclinic.security.model.UserDetailsImpl;
import com.bukup.vetclinic.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class VisitController {
    private final EmployeeService employeeService;
    private final VisitorService visitorService;
    private final ServiceTypeService serviceTypeService;
    private final VisitService visitService;
    private final PetService petService;
    private final ScheduleService scheduleService;

    public VisitController(EmployeeService employeeService, VisitorService visitorService,
                           ServiceTypeService serviceTypeService, VisitService visitService, PetService petService,
                           ScheduleService scheduleService) {
        this.employeeService = employeeService;
        this.visitorService = visitorService;
        this.serviceTypeService = serviceTypeService;
        this.visitService = visitService;
        this.petService = petService;
        this.scheduleService = scheduleService;
    }



    @GetMapping("/book")
    public String getSchedule(Model model,
                              @RequestParam(value = "employeeId") long employeeId,
                              @RequestParam(value = "serviceTypeId") long serviceTypeId,
                              @RequestParam(value = "pets") String petIds,
                              @RequestParam(value = "week") int weekNumber) {
        validateWeekNumber(weekNumber);
        List<Pet> pets = getPets(petIds);
        ServiceType serviceType = getServiceType(serviceTypeId);
        Employee employee = getEmployee(employeeId);
        Schedule schedule = employee.getSchedule();
        List<LocalDate> week = getWeekDates(weekNumber);
        Map<LocalDateTime, VisitScheduleSegment> visitScheduleSegments = getScheduleSegments(schedule, week);

        model.addAttribute("concreteVisitRequest", new VisitRequest());
        model.addAttribute("visitScheduleSegments", visitScheduleSegments);
        model.addAttribute("schedule", schedule);
        model.addAttribute("week", week);
        model.addAttribute("weekNumber", weekNumber);
        model.addAttribute("employee", employee);
        model.addAttribute("serviceType", serviceType);
        model.addAttribute("pets", pets);
        model.addAttribute("petIds", petIds);
        return "book-schedule";
    }

    @PostMapping("/book")
    public String bookVisit(@ModelAttribute("concreteVisitRequest") VisitRequest visitRequest,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Visitor visitor = getVisitor(userDetails.getId());
        Employee employee = getEmployee(visitRequest.getEmployeeId());
        Schedule schedule = employee.getSchedule();
        TimeSlot timeSlot = createTimeSlot(visitRequest, schedule);
        schedule.addTimeSlot(timeSlot);
        scheduleService.update(employee.getSchedule());
        Visit visit = createVisit(visitRequest, visitor, employee, timeSlot);
        visitService.create(visit);
        return "";
    }

    private void validateWeekNumber(int weekNumber) {
        if (weekNumber > 4 || weekNumber < 1) {
            throw new IllegalArgumentException("Week has to be between 1 and 4");
        }
    }

    private List<Pet> getPets(String petIds) {
        List<Long> petIdList = Arrays.stream(petIds.split(","))
                .map(Long::valueOf)
                .toList();

        return petIdList.stream()
                .map(petService::findById)
                .collect(Collectors.toList());
    }

    private ServiceType getServiceType(long serviceTypeId) {
        return serviceTypeService.findById(serviceTypeId);
    }

    private Employee getEmployee(long employeeId) {
        return employeeService.findById(employeeId);
    }

    private List<LocalDate> getWeekDates(int weekNumber) {
        final int firstWeekDate = (weekNumber - 1) * 7;
        return IntStream.range(firstWeekDate, firstWeekDate + 6)
                .mapToObj(i -> LocalDate.now().plusDays(i))
                .collect(Collectors.toList());
    }

    private Visitor getVisitor(long visitorId) {
        return visitorService.findById(visitorId);
    }

    private TimeSlot createTimeSlot(VisitRequest visitRequest, Schedule schedule) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartTime(visitRequest.getStartTime());
        timeSlot.setEndTime(visitRequest.getEndTime());
        timeSlot.setSchedule(schedule);
        return timeSlot;
    }

    private Visit createVisit(VisitRequest visitRequest, Visitor visitor, Employee employee, TimeSlot timeSlot) {
        Visit visit = new Visit();
        visit.setVisitor(visitor);
        visit.setEmployee(employee);
        visit.setTimeSlot(timeSlot);
        visit.setPets(visitRequest.getPetIds().stream()
                .map(petService::findById)
                .collect(Collectors.toSet()));
        visit.setServiceType(serviceTypeService.findById(visitRequest.getServiceTypeId()));
        return visit;
    }

    private static Map<LocalDateTime, VisitScheduleSegment> getScheduleSegments(Schedule schedule, List<LocalDate> week) {
        Map<LocalDateTime, VisitScheduleSegment> visitScheduleSegments = new TreeMap<>();
        LocalTime startTime = schedule.getDayStartTime();
        LocalTime endTime = schedule.getDayEndTime();
        Duration slotDuration = schedule.getTimeSlotDuration();

        for (LocalDate date : week) {
            LocalTime time = startTime;
            while (!time.isAfter(endTime)) {
                LocalDateTime startDateTime = LocalDateTime.of(date, time);
                LocalDateTime endDateTime = startDateTime.plusMinutes(slotDuration.toMinutes());

                VisitScheduleSegment visitScheduleSegment = new VisitScheduleSegment();
                visitScheduleSegment.setStartTime(startDateTime);
                visitScheduleSegment.setEndTime(endDateTime);

                visitScheduleSegments.put(startDateTime, visitScheduleSegment);

                time = time.plusMinutes(slotDuration.toMinutes());
            }
        }
        return visitScheduleSegments;
    }
}
