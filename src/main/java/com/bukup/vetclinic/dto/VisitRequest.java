package com.bukup.vetclinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VisitRequest {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long serviceTypeId;
    private long employeeId;
    private List<Long> petIds;

}
