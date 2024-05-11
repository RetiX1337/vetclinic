package com.bukup.vetclinic.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VisitScheduleSegment {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
