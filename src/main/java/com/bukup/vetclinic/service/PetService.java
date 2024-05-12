package com.bukup.vetclinic.service;

import com.bukup.vetclinic.model.Pet;

import java.util.List;

public interface PetService {
    Pet create(Pet pet);

    Pet findById(long id);

    Pet update(Pet pet);

    void delete(long id);

    List<Pet> getAll();

    List<Pet> getAllByOwner(Long ownerId);

    boolean isPetOwner(long ownerId, long petId);
}
