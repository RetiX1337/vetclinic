package com.bukup.vetclinic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceTypeRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    private long categoryId;
}
