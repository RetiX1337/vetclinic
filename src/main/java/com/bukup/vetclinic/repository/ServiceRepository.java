package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}