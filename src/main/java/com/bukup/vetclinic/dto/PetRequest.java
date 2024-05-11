package com.bukup.vetclinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PetRequest {
    private String dateOfBirth;
    private String animalType;
    private String name;
}
