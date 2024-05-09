package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}