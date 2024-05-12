package com.bukup.vetclinic.security.utils;

import com.bukup.vetclinic.service.EmployeeService;
import com.bukup.vetclinic.service.PetService;
import com.bukup.vetclinic.service.VisitService;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ControllerHelper {
    private final PetService petService;
    private final VisitService visitService;

    public ControllerHelper(PetService petService, VisitService visitService) {
        this.petService = petService;
        this.visitService = visitService;
    }

    public boolean isPetOwner(long ownerId, long petId) {
        return petService.isPetOwner(ownerId, petId);
    }

    public boolean isPetsOwnerVisitRequest(long ownerId, String petIds) {
        return Arrays.stream(petIds.split(","))
                .map(Long::parseLong)
                .allMatch(petId -> petService.isPetOwner(ownerId, petId));
    }

    public boolean isEmployeeVisit(long employeeId, long visitId) {
        return visitService.isEmployeeVisit(employeeId, visitId);
    }
}
