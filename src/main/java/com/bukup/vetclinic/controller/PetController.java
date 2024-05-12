package com.bukup.vetclinic.controller;

import com.bukup.vetclinic.dto.PetRequest;
import com.bukup.vetclinic.model.Pet;
import com.bukup.vetclinic.model.Visitor;
import com.bukup.vetclinic.security.model.UserDetailsImpl;
import com.bukup.vetclinic.security.utils.ControllerHelper;
import com.bukup.vetclinic.service.PetService;
import com.bukup.vetclinic.service.VisitorService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;
    private final VisitorService visitorService;
    private final ControllerHelper controllerHelper;

    public PetController(PetService petService, VisitorService visitorService, ControllerHelper controllerHelper) {
        this.petService = petService;
        this.visitorService = visitorService;
        this.controllerHelper = controllerHelper;
    }

    @PreAuthorize("hasAuthority('ADMIN') || @controllerHelper.isPetOwner(authentication.principal.id, #id)")
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

    @PreAuthorize("hasAuthority('ADMIN') || @controllerHelper.isPetOwner(authentication.principal.id, #id)")
    @PostMapping("/{id}/delete")
    public String deletePet(@PathVariable Long id) {
        final Pet pet = petService.findById(id);
        petService.delete(id);
        return "redirect:/profile/" + pet.getOwner().getUserId();
    }

    @PostMapping
    public String createPet(@Valid @ModelAttribute("pet") PetRequest petRequest, BindingResult result,
                            @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pet", petRequest);
            return "pets/newPet";
        }
        final Visitor owner = visitorService.findById(userDetails.getId());

        final Pet pet = new Pet();
        pet.setName(petRequest.getName());
        pet.setAnimalType(petRequest.getAnimalType());
        pet.setDateOfBirth(LocalDate.parse(petRequest.getDateOfBirth()));
        pet.setOwner(owner);
        petService.create(pet);
        return "redirect:/profile/" + pet.getOwner().getUserId();
    }

    @PreAuthorize("hasAuthority('ADMIN') || @controllerHelper.isPetOwner(authentication.principal.id, #id)")
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

    @PreAuthorize("hasAuthority('ADMIN') || @controllerHelper.isPetOwner(authentication.principal.id, #id)")
    @PostMapping("/{id}")
    public String updatePet(@PathVariable Long id, @Valid @ModelAttribute("pet") PetRequest petRequest,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pet", petRequest);
            model.addAttribute("petId", id);
            return "pets/editPet";
        }
        final Pet pet = petService.findById(id);
        pet.setName(petRequest.getName());
        pet.setAnimalType(petRequest.getAnimalType());
        pet.setDateOfBirth(LocalDate.parse(petRequest.getDateOfBirth()));
        petService.update(pet);
        return "redirect:/profile/" + pet.getOwner().getUserId();
    }
}
