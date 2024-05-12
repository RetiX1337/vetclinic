package com.bukup.vetclinic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetRequest {
    @NotBlank
    private String dateOfBirth;
    @NotBlank
    private String animalType;
    @NotBlank
    private String name;
}
