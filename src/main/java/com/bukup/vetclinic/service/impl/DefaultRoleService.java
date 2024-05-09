package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.Role;
import com.bukup.vetclinic.repository.RoleRepository;
import com.bukup.vetclinic.service.RoleService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultRoleService implements RoleService {
    private final RoleRepository roleRepository;

    public DefaultRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role create(Role role) {
        checkIfRoleExistsByName(role.getName());
        return roleRepository.save(role);
    }

    @Override
    public Role readById(long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Role with id " + id + " not found"));
    }

    @Override
    public Role readByName(String name) {
        return Optional.ofNullable(roleRepository.findByName(name)).orElseThrow(
                () -> new EntityNotFoundException("Role with name " + name + " not found"));
    }

    private void checkIfRoleExistsByName(String name) {
        if (roleRepository.existsByName(name)) {
            throw new EntityExistsException("Role with name " + name + " already exists");
        }
    }
}