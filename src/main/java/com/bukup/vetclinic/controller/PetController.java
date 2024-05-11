package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.dto.PetRequest;
import com.bukup.vetclinic.model.Pet;
import com.bukup.vetclinic.model.Visitor;
import com.bukup.vetclinic.security.model.UserDetailsImpl;
import com.bukup.vetclinic.service.PetService;
import com.bukup.vetclinic.service.VisitorService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;
    private final VisitorService visitorService;

    public PetController(PetService petService, VisitorService visitorService) {
        this.petService = petService;
        this.visitorService = visitorService;
    }

    @GetMapping
    public String getAllPets(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("pets", petService.getAllByOwner(userDetails.getId()));
        return "pets/pets";
    }

    @GetMapping("/{id}")
    public String getPet(@PathVariable Long id, Model model) {
        model.addAttribute("pet", petService.findById(id));
        return "pets/pet";
    }

    @GetMapping("/new")
    public String newPet(Model model) {
        model.addAttribute("pet", new PetRequest());
        return "pets/newPet";
    }

    @PostMapping
    public String createPet(@ModelAttribute("pet") PetRequest petRequest,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        final Visitor owner = visitorService.findById(userDetails.getId());

        final Pet pet = new Pet();
        pet.setName(petRequest.getName());
        pet.setAnimalType(petRequest.getAnimalType());
        pet.setDateOfBirth(LocalDate.parse(petRequest.getDateOfBirth()));
        pet.setOwner(owner);
        petService.create(pet);
        return "redirect:/pets";
    }

    @GetMapping("/{id}/edit")
    public String editPet(@PathVariable Long id, Model model) {
        final Pet pet = petService.findById(id);
        final PetRequest petRequest = new PetRequest();
        petRequest.setName(pet.getName());
        petRequest.setDateOfBirth(String.valueOf(pet.getDateOfBirth()));
        petRequest.setAnimalType(pet.getAnimalType());
        model.addAttribute("pet", petRequest);
        model.addAttribute("petId", id);
        return "pets/editPet";
    }

    @PostMapping("/{id}")
    public String updatePet(@PathVariable Long id, @ModelAttribute("pet") PetRequest petRequest) {
        final Pet pet = petService.findById(id);
        pet.setName(petRequest.getName());
        pet.setAnimalType(petRequest.getAnimalType());
        pet.setDateOfBirth(LocalDate.parse(petRequest.getDateOfBirth()));
        petService.update(pet);
        return "redirect:/pets";
    }
}
