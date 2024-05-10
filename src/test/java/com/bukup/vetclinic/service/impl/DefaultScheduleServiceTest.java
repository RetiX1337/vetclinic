package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.Employee;
import com.bukup.vetclinic.model.Schedule;
import com.bukup.vetclinic.model.ScheduleDay;
import com.bukup.vetclinic.model.TimeSlot;
import com.bukup.vetclinic.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    private DefaultScheduleService scheduleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        scheduleService = new DefaultScheduleService(scheduleRepository);
    }

    @Test
    public void testCreateSchedule() {
        Employee employee = new Employee();
        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(i -> i.getArguments()[0]);

        Schedule schedule = scheduleService.createSchedule(employee);

        LocalDate lastScheduleDayDate = LocalDate.now().plusMonths(1);
        assertNotNull(schedule);
        assertTrue(schedule.getScheduleDays().stream()
                        .anyMatch(scheduleDay -> scheduleDay.getDate().equals(lastScheduleDayDate)));

        for (ScheduleDay day : schedule.getScheduleDays()) {
            assertEquals(8, day.getTimeSlots().size());
            for (TimeSlot slot : day.getTimeSlots()) {
                assertTrue(slot.getStartTime().getHour() >= 8);
                assertTrue(slot.getEndTime().getHour() <= 16);
            }
        }

        verify(scheduleRepository, times(1)).save(any(Schedule.class));
    }

    @Test
    public void testGetScheduleDaysForWeek() {
        Employee employee = new Employee();
        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(i -> i.getArguments()[0]);

        Schedule schedule = scheduleService.createSchedule(employee);
        LocalDate startDate = LocalDate.now();

        List<ScheduleDay> week = scheduleService.getScheduleDaysForWeek(schedule, startDate);

        assertEquals(LocalDate.now().plusDays(6), week.stream()
                .map(ScheduleDay::getDate)
                .max(LocalDate::compareTo)
                .get());
        assertNotNull(week);
    }

    @Test
    public void testEnsureSchedule() {
        Schedule schedule = new Schedule();
        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(i -> i.getArguments()[0]);

        scheduleService.ensureSchedule(schedule);

        LocalDate lastScheduleDayDate = LocalDate.now().plusMonths(1);
        assertNotNull(schedule);
        assertTrue(schedule.getScheduleDays().stream()
                .anyMatch(scheduleDay -> scheduleDay.getDate().equals(lastScheduleDayDate)));

        for (ScheduleDay day : schedule.getScheduleDays()) {
            assertEquals(8, day.getTimeSlots().size());
            for (TimeSlot slot : day.getTimeSlots()) {
                assertTrue(slot.getStartTime().getHour() >= 8);
                assertTrue(slot.getEndTime().getHour() <= 16);
            }
        }

        verify(scheduleRepository, times(1)).save(any(Schedule.class));
    }
}
