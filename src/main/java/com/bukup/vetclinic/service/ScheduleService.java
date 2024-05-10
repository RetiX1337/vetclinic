package com.bukup.vetclinic.service;

import com.bukup.vetclinic.model.Employee;
import com.bukup.vetclinic.model.Schedule;
import com.bukup.vetclinic.model.ScheduleDay;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    Schedule createSchedule(Employee employee);

    List<ScheduleDay> getScheduleDaysForWeek(Schedule schedule, LocalDate startDate);

    void ensureSchedule(Schedule schedule);
}
