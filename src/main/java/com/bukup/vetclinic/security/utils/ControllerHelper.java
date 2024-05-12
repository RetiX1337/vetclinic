package com.bukup.vetclinic.security.utils;

import com.bukup.vetclinic.service.PetService;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ControllerHelper {
    private final PetService petService;

    public ControllerHelper(PetService petService) {
        this.petService = petService;
    }

    public boolean isPetOwner(long ownerId, long petId) {
        return petService.isPetOwner(ownerId, petId);
    }

    public boolean isPetsOwnerVisitRequest(long ownerId, String petIds) {
        return Arrays.stream(petIds.split(","))
                .map(Long::parseLong)
                .allMatch(petId -> petService.isPetOwner(ownerId, petId));
    }
}
