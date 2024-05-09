package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}