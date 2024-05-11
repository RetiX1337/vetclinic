package com.bukup.vetclinic.service.impl;

import com.bukup.vetclinic.model.Pet;
import com.bukup.vetclinic.model.Visitor;
import com.bukup.vetclinic.repository.PetRepository;
import com.bukup.vetclinic.service.PetService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class DefaultPetService implements PetService {
    private final PetRepository petRepository;

    public DefaultPetService(final PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet create(Pet pet) {
        checkIfPetExistsByNameForVisitor(pet.getName(), pet.getOwner());
        return petRepository.save(pet);
    }

    @Override
    public Pet findById(long id) {
        return petRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Pet with id " + id + " not found"));
    }

    @Override
    public Pet update(Pet category) {
        final long id = category.getId();
        if (petRepository.existsById(id)) {
            return petRepository.save(category);
        }
        throw new EntityNotFoundException("Pet with id " + id + " not found");
    }

    @Override
    public void delete(long id) {
        petRepository.delete(findById(id));
    }

    @Override
    public List<Pet> getAll() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> getAllByOwner(final Long ownerId) {
        return petRepository.findAllByOwner_user_id(ownerId);
    }

    private void checkIfPetExistsByNameForVisitor(final String name, final Visitor visitor) {
        if (petRepository.existsByNameAndOwner(name, visitor)) {
            throw new EntityExistsException("Pet with name " + name + " for Visitor " + visitor.getUserId() + " already exists");
        }
    }
}
