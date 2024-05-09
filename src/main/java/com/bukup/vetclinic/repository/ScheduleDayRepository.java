package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.ScheduleDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleDayRepository extends JpaRepository<ScheduleDay, Long> {
}