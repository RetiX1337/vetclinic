package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.Employee;
import com.bukup.vetclinic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e JOIN e.categories c JOIN c.serviceTypes s WHERE s.name = :serviceName")
    List<Employee> findEmployeesByServiceType(@Param("serviceName") String serviceName);
}
