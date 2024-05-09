package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
