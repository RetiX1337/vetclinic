package com.bukup.vetclinic.repository;

import com.bukup.vetclinic.model.Pet;
import com.bukup.vetclinic.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    boolean existsByNameAndOwner(String name, Visitor owner);

    List<Pet> findAllByOwner_user_id(Long ownerId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Pet p " +
            "WHERE p.id = :petId AND p.owner.userId = :ownerId")
    boolean isPetOwner(@Param("ownerId") long ownerId, @Param("petId") long petId);
}