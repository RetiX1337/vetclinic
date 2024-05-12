package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    List<ServiceType> findServiceTypeByNameContainingIgnoreCase(String serviceNamePart);
    boolean existsByName(String name);

    default List<ServiceType> findAny(final int limit) {
        Pageable pages = PageRequest.of(0, limit);
        Page<ServiceType> limitEntities = findAll(pages);
        return limitEntities.getContent();
    }
}