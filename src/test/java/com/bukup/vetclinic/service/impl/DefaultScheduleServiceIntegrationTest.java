package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.Employee;
import com.bukup.vetclinic.model.Schedule;
import com.bukup.vetclinic.model.ScheduleDay;
import com.bukup.vetclinic.model.TimeSlot;
import com.bukup.vetclinic.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class DefaultScheduleServiceIntegrationTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    private DefaultScheduleService scheduleService;

    @BeforeEach
    public void setUp() {
        scheduleService = new DefaultScheduleService(scheduleRepository);
    }

    @Test
    public void testCreateSchedule() {
        Employee employee = new Employee();

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
    }

    @Test
    public void testGetScheduleDaysForWeek() {
        Employee employee = new Employee();

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
    }
}
