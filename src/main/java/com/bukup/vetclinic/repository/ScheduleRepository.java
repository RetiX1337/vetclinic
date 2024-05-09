package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}