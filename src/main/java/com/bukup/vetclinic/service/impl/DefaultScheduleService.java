package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.Schedule;
import com.bukup.vetclinic.repository.ScheduleRepository;
import com.bukup.vetclinic.service.ScheduleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class DefaultScheduleService implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public DefaultScheduleService(final ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule create(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule update(Schedule schedule) {
        final long id = schedule.getId();
        if (scheduleRepository.existsById(id)) {
            return scheduleRepository.save(schedule);
        }
        throw new EntityNotFoundException("Schedule with id " + id + " not found");

    }

}
