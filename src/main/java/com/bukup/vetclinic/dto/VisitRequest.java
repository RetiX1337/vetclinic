package com.bukup.vetclinic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitRequest {
    @NotBlank
    private String delimitedTime;
    private long serviceTypeId;
    private long employeeId;
    @NotBlank
    private String petIds;

}
