package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.Visit;
import com.bukup.vetclinic.repository.VisitRepository;
import com.bukup.vetclinic.service.VisitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultVisitService implements VisitService {
    private final VisitRepository visitRepository;

    public DefaultVisitService(final VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Visit create(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public Visit findById(long id) {
        return visitRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Visitor with id " + id + " not found"));
    }

    @Override
    public Visit update(Visit visit) {
        final long id = visit.getId();
        if (visitRepository.existsById(id)) {
            return visitRepository.save(visit);
        }
        throw new EntityNotFoundException("Visitor with id " + id + " not found");
    }

    @Override
    public void delete(long id) {
        visitRepository.delete(findById(id));
    }

    @Override
    public List<Visit> getAll() {
        return visitRepository.findAll();
    }
}
