package com.bukup.vetclinic.service;

import com.bukup.vetclinic.model.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceTypeService {
    ServiceType create(ServiceType serviceType);

    ServiceType findById(long id);

    ServiceType update(ServiceType serviceType);

    void delete(long id);

    List<ServiceType> getAll();

    List<ServiceType> getTop(final int limit);

    Page<ServiceType> getAllByNamePartAndCategory(String serviceNamePart, List<Long> categoryIds, Pageable pageable);
}
