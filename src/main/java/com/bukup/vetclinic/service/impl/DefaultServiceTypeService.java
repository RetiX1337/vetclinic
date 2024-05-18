package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.ServiceType;
import com.bukup.vetclinic.repository.ServiceTypeRepository;
import com.bukup.vetclinic.service.ServiceTypeService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public List<ServiceType> getTop(int limit) {
        return serviceTypeRepository.findAny(limit);
    }

    @Override
    public Page<ServiceType> getAllByNamePartAndCategory(String serviceNamePart, List<Long> categoryIds, Pageable pageable) {
        return serviceTypeRepository.findAll(Specification
                .where(serviceNamePart == null ? null : nameContains(serviceNamePart))
                .and(categoryIds == null || categoryIds.isEmpty() ? null : categoryIsIn(categoryIds)), pageable);
    }

    private Specification<ServiceType> nameContains(String serviceNamePart) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + serviceNamePart + "%");
    }

    private Specification<ServiceType> categoryIsIn(List<Long> categoryIds) {
        return (root, query, cb) -> root.get("category").get("id").in(categoryIds);
    }

    private void checkIfServiceTypeExistsByName(final String name) {
        if (serviceTypeRepository.existsByName(name)) {
            throw new EntityExistsException("ServiceType with name " + name + " already exists");
        }
    }
}
