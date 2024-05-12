package com.bukup.vetclinic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceTypeRequest {
    private String name;
    private long categoryId;
}
