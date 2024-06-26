package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByType(final String type);
}
