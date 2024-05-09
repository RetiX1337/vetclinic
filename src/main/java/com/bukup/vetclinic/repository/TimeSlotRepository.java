package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
}