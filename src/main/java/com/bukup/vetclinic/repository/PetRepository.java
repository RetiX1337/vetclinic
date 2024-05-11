package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.Pet;
import com.bukup.vetclinic.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    boolean existsByNameAndOwner(String name, Visitor owner);

    List<Pet> findAllByOwner_user_id(Long ownerId);
}