package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    List<ServiceType> findServiceTypeByNameContainingIgnoreCase(String serviceNamePart);
    boolean existsByName(String name);
}