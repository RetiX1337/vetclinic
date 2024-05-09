package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    boolean existsByEmail(String email);
}
