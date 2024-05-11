package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.User;
import com.bukup.vetclinic.model.Visitor;
import com.bukup.vetclinic.repository.EmployeeRepository;
import com.bukup.vetclinic.repository.VisitorRepository;
import com.bukup.vetclinic.service.VisitorService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class DefaultVisitorService implements VisitorService {
    private final VisitorRepository visitorRepository;
    private final EmployeeRepository employeeRepository;

    public DefaultVisitorService(final VisitorRepository visitorRepository, final EmployeeRepository employeeRepository) {
        this.visitorRepository = visitorRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Visitor create(Visitor visitor) {
        checkIfVisitorExistsByUser(visitor.getUser().getId());
        return visitorRepository.save(visitor);
    }

    @Override
    public Visitor findById(long id) {
        return visitorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Visitor with id " + id + " not found"));
    }

    @Override
    public Visitor update(Visitor visitor) {
        final long id = visitor.getUserId();
        if (visitorRepository.existsById(id)) {
            return visitorRepository.save(visitor);
        }
        throw new EntityNotFoundException("Visitor with id " + id + " not found");
    }

    @Override
    public void delete(long id) {
        visitorRepository.delete(findById(id));
    }

    @Override
    public List<Visitor> getAll() {
        return visitorRepository.findAll();
    }


    @Override
    public boolean existsByUserId(long userId) {
        return visitorRepository.existsById(userId);
    }

    private void checkIfVisitorExistsByUser(long userId) {
        if (existsByUserId(userId) || employeeRepository.existsById(userId)) {
            throw new EntityExistsException("Visitor with user " + userId + " already exists");
        }
    }
}
