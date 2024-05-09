package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.User;
import com.bukup.vetclinic.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    boolean existsByUser(final User user);
}
