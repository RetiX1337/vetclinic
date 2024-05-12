package com.bukup.vetclinic.service;

import com.bukup.vetclinic.model.ServiceType;

import java.util.List;

public interface ServiceTypeService {
    ServiceType create(ServiceType serviceType);

    ServiceType findById(long id);

    ServiceType update(ServiceType serviceType);

    void delete(long id);

    List<ServiceType> getAll();

    List<ServiceType> getAllByNamePart(String serviceNamePart);

    List<ServiceType> getTop(final int limit);
}
