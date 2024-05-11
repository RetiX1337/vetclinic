package com.bukup.vetclinic.service;

import com.bukup.vetclinic.model.Employee;
import com.bukup.vetclinic.model.ServiceType;

import java.util.List;

public interface EmployeeService {
    Employee create(Employee employee);

    Employee findById(long id);

    Employee update(Employee employee);

    void delete(long id);

    List<Employee> getAll();

    boolean existsByUserId(long userId);

    List<Employee> getAllByServiceType(final String serviceName);
}
