package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Visit v " +
            "WHERE v.id = :visitId AND v.employee.userId = :employeeId")
    boolean isEmployeeVisit(@Param("employeeId") long employeeId, @Param("visitId") long visitId);
}