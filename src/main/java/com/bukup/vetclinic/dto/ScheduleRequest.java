package com.bukup.vetclinic.dto;

import com.bukup.vetclinic.model.Schedule;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalTime;

@Getter
@Setter
public class ScheduleRequest {
    @NotNull
    private LocalTime dayStartTime;
    @NotNull
    private LocalTime dayEndTime;
    private Duration timeSlotDuration;

    public ScheduleRequest() {}

    public ScheduleRequest(Schedule schedule) {
        this.dayStartTime = schedule.getDayStartTime();
        this.dayEndTime = schedule.getDayEndTime();
        this.timeSlotDuration = schedule.getTimeSlotDuration();
    }
}
