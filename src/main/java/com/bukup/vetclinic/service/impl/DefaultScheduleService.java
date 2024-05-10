package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.Employee;
import com.bukup.vetclinic.model.Schedule;
import com.bukup.vetclinic.model.ScheduleDay;
import com.bukup.vetclinic.model.TimeSlot;
import com.bukup.vetclinic.repository.ScheduleRepository;
import com.bukup.vetclinic.service.ScheduleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class DefaultScheduleService implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public DefaultScheduleService(final ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule createSchedule(Employee employee) {
        Schedule schedule = new Schedule();
        fillScheduleMonth(schedule);
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleDay> getScheduleDaysForWeek(Schedule schedule, LocalDate startDate) {
        List<ScheduleDay> week = new ArrayList<>();
        LocalDate endOfWeek = startDate.plusDays(6);

        for (ScheduleDay day : schedule.getScheduleDays()) {
            LocalDate dayDate = day.getDate();
            if ((dayDate.isAfter(startDate) || dayDate.isEqual(startDate))
                    && (dayDate.isBefore(endOfWeek) || dayDate.isEqual(endOfWeek))) {
                week.add(day);
            }
        }

        return week;
    }

    @Override
    public void ensureSchedule(Schedule schedule) {
        fillScheduleMonth(schedule);
        scheduleRepository.save(schedule);
    }

    private void fillScheduleMonth(Schedule schedule) {
        List<ScheduleDay> days = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        LocalDate currentDatePlusMonth = currentDate.plusMonths(1);

        for (LocalDate date = currentDate; !date.isAfter(currentDatePlusMonth); date = date.plusDays(1)) {
            ScheduleDay day = new ScheduleDay();
            day.setDate(date);
            day.setTimeSlots(createHourlyTimeSlots(date));
            days.add(day);
        }

        schedule.setScheduleDays(days);
    }

    private List<TimeSlot> createHourlyTimeSlots(LocalDate date) {
        List<TimeSlot> timeSlots = new ArrayList<>();
        LocalDateTime startDateTime = date.atTime(8, 0);
        LocalDateTime endDateTime = date.atTime(16, 0);

        for (LocalDateTime dateTime = startDateTime; dateTime.isBefore(endDateTime); dateTime = dateTime.plusHours(1)) {
            TimeSlot slot = new TimeSlot();
            slot.setStartTime(dateTime);
            slot.setEndTime(dateTime.plusHours(1));
            timeSlots.add(slot);
        }

        return timeSlots;
    }
}
