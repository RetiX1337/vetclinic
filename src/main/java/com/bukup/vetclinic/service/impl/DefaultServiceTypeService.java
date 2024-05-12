package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.ServiceType;
import com.bukup.vetclinic.repository.ServiceTypeRepository;
import com.bukup.vetclinic.service.ServiceTypeService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class DefaultServiceTypeService implements ServiceTypeService {
    private final ServiceTypeRepository serviceTypeRepository;

    public DefaultServiceTypeService(final ServiceTypeRepository serviceTypeRepository)
    {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    public ServiceType create(ServiceType serviceType) {
        checkIfServiceTypeExistsByName(serviceType.getName());
        return serviceTypeRepository.save(serviceType);
    }

    @Override
    public ServiceType findById(long id) {
        return serviceTypeRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("ServiceType with id " + id + " not found"));
    }

    @Override
    public ServiceType update(ServiceType serviceType) {
        final long id = serviceType.getId();
        if (serviceTypeRepository.existsById(id)) {
            return serviceTypeRepository.save(serviceType);
        }
        throw new EntityNotFoundException("ServiceType with id " + id + " not found");
    }

    @Override
    public void delete(long id) {
        serviceTypeRepository.delete(findById(id));
    }

    @Override
    public List<ServiceType> getAll() {
        return serviceTypeRepository.findAll();
    }

    @Override
    public List<ServiceType> getAllByNamePart(String serviceNamePart) {
        return serviceTypeRepository.findServiceTypeByNameContainingIgnoreCase(serviceNamePart);
    }

    private void checkIfServiceTypeExistsByName(final String name) {
        if (serviceTypeRepository.existsByName(name)) {
            throw new EntityExistsException("ServiceType with name " + name + " already exists");
        }
    }
}
