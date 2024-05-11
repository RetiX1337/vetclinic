package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.dto.VisitRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class VisitController {
    private final EmployeeService employeeService;
    private final VisitorService visitorService;
    private final CategoryService categoryService;
    private final VisitService visitService;
    private final PetService petService;
    private final ScheduleService scheduleService;

    public VisitController(EmployeeService employeeService, VisitorService visitorService,
                           CategoryService categoryService, VisitService visitService, PetService petService,
                           ScheduleService scheduleService) {
        this.employeeService = employeeService;
        this.visitorService = visitorService;
        this.categoryService = categoryService;
        this.visitService = visitService;
        this.petService = petService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/book")
    public String getSchedule(Model model,
                              @RequestParam(value = "employeeId") long employeeId,
                              @RequestParam(value = "categoryId") long categoryId,
                              @RequestParam(value = "week") int weekNumber) {
        if (weekNumber > 4 || weekNumber < 1) {
            throw new IllegalArgumentException("Week has to be between 1 and 4");
        }
        Employee employee = employeeService.findById(employeeId);
        Schedule schedule = employee.getSchedule();
        model.addAttribute("schedule", schedule);
        final int firstWeekDate = (weekNumber - 1) * 7;
        List<LocalDate> week = IntStream.range(firstWeekDate, firstWeekDate + 6)
                .mapToObj(i -> LocalDate.now().plusDays(i))
                .collect(Collectors.toList());

        Map<LocalDateTime, VisitRequest> visitRequests = getTimeVisitRequestMap(employeeId, categoryId, schedule, week);
        model.addAttribute("concreteVisitRequest", new VisitRequest());
        model.addAttribute("visitRequests", visitRequests);

        model.addAttribute("week", week);
        model.addAttribute("weekNumber", weekNumber);
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("categoryId", categoryId);
        return "book-schedule";
    }

    @PostMapping("/book")
    public String bookVisit(@ModelAttribute("concreteVisitRequest") VisitRequest visitRequest,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        final Visitor visitor = visitorService.findById(userDetails.getId());
        final Employee employee = employeeService.findById(visitRequest.getEmployeeId());
        final Schedule schedule = employee.getSchedule();
        final TimeSlot timeSlot = new TimeSlot();
        timeSlot.setStartTime(visitRequest.getStartTime());
        timeSlot.setEndTime(visitRequest.getEndTime());
        timeSlot.setSchedule(schedule);
        schedule.addTimeSlot(timeSlot);
        scheduleService.update(employee.getSchedule());
        final Visit visit = new Visit();
        visit.setVisitor(visitor);
        visit.setEmployee(employee);
        visit.setTimeSlot(timeSlot);
//        visit.setPets(visitRequest.getPetIds().stream()
//                .map(petService::findById)
//                .collect(Collectors.toSet()));
        visit.setCategory(categoryService.findById(visitRequest.getCategoryId()));
        visitService.create(visit);
        return "";
    }

    private static Map<LocalDateTime, VisitRequest> getTimeVisitRequestMap(long employeeId, long categoryId, Schedule schedule, List<LocalDate> week) {
        Map<LocalDateTime, VisitRequest> visitRequests = new TreeMap<>();
        LocalTime startTime = schedule.getDayStartTime();
        LocalTime endTime = schedule.getDayEndTime();
        Duration slotDuration = schedule.getTimeSlotDuration();

        for (LocalDate date : week) {
            LocalTime time = startTime;
            while (!time.isAfter(endTime)) {
                LocalDateTime startDateTime = LocalDateTime.of(date, time);
                LocalDateTime endDateTime = startDateTime.plusMinutes(slotDuration.toMinutes());

                VisitRequest visitRequest = new VisitRequest();
                visitRequest.setStartTime(startDateTime);
                visitRequest.setEndTime(endDateTime);
                visitRequest.setCategoryId(categoryId);
                visitRequest.setEmployeeId(employeeId);

                visitRequests.put(startDateTime, visitRequest);

                time = time.plusMinutes(slotDuration.toMinutes());
            }
        }
        return visitRequests;
    }
}
