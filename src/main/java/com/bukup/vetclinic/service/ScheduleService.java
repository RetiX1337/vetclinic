package com.bukup.vetclinic.service;

import com.bukup.vetclinic.model.Employee;
import com.bukup.vetclinic.model.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    Schedule create(Schedule schedule);

    Schedule update(Schedule schedule);
}
