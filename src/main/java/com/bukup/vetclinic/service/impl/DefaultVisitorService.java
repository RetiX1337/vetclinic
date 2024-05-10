package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.User;
import com.bukup.vetclinic.model.Visitor;
import com.bukup.vetclinic.repository.VisitorRepository;
import com.bukup.vetclinic.service.VisitorService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultVisitorService implements VisitorService {
    private final VisitorRepository visitorRepository;

    public DefaultVisitorService(final VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    @Override
    public Visitor create(Visitor visitor) {
        checkIfVisitorExistsByUser(visitor.getUser());
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

    private void checkIfVisitorExistsByUser(User user) {
        if (visitorRepository.existsByUser(user)) {
            throw new EntityExistsException("Visitor with user " + user.getId() + " already exists");
        }
    }
}
