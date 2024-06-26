package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.Employee;
import com.bukup.vetclinic.model.User;
import com.bukup.vetclinic.model.Visitor;
import com.bukup.vetclinic.repository.UserRepository;
import com.bukup.vetclinic.service.EmployeeService;
import com.bukup.vetclinic.service.UserService;
import com.bukup.vetclinic.service.VisitorService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final VisitorService visitorService;
    private final EmployeeService employeeService;

    public DefaultUserService(UserRepository userRepository, VisitorService visitorService,
                              EmployeeService employeeService) {
        this.userRepository = userRepository;
        this.visitorService = visitorService;
        this.employeeService = employeeService;
    }

    @Override
    public User create(User user) {
        checkIfUserExistsByEmail(user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public Visitor createVisitorUser(Visitor visitor) {
        create(visitor.getUser());
        return visitorService.create(visitor);
    }

    @Override
    public Employee createEmployeeUser(Employee employee) {
        create(employee.getUser());
        return employeeService.create(employee);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public User findUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findUserByEmail(email)).orElseThrow(
                () -> new EntityNotFoundException("User with email " + email + " not found"));
    }

    @Override
    public User update(User user) {
        final long id = user.getId();
        if (userRepository.existsById(id)) {
            return userRepository.save(user);
        }
        throw new EntityNotFoundException("User with id " + id + " not found");
    }

    @Override
    public void delete(long id) {
        userRepository.delete(findById(id));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    private void checkIfUserExistsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EntityExistsException("User with email " + email + " already exists");
        }
    }
}
