package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    boolean existsByName(String name);
}