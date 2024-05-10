package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.Employee;
import com.bukup.vetclinic.model.User;
import com.bukup.vetclinic.repository.EmployeeRepository;
import com.bukup.vetclinic.service.EmployeeService;
import com.bukup.vetclinic.service.ScheduleService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class DefaultEmployeeService implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ScheduleService scheduleService;

    public DefaultEmployeeService(final EmployeeRepository employeeRepository, ScheduleService scheduleService) {
        this.employeeRepository = employeeRepository;
        this.scheduleService = scheduleService;
    }

    @Override
    public Employee create(Employee employee) {
        checkIfEmployeeExistsByUser(employee.getUser());
        scheduleService.createSchedule(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findById(long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Employee with id " + id + " not found"));
    }

    @Override
    public Employee update(Employee employee) {
        final long id = employee.getUserId();
        if (employeeRepository.existsById(id)) {
            return employeeRepository.save(employee);
        }
        throw new EntityNotFoundException("Employee with id " + id + " not found");
    }

    @Override
    public void delete(long id) {
        employeeRepository.delete(findById(id));
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    private void checkIfEmployeeExistsByUser(User user) {
        if (employeeRepository.existsByUser(user)) {
            throw new EntityExistsException("Employee with user " + user.getId() + " already exists");
        }
    }
}
