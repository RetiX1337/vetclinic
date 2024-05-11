package com.bukup.vetclinic.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "schedules")
public class Schedule
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@OneToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
	private List<TimeSlot> bookedTimeSlots;

	@Column(name = "day_start_time")
	private LocalTime dayStartTime;

	@Column(name = "day_end_time")
	private LocalTime dayEndTime;

	@Column(name = "time_slot_duration")
	private Duration timeSlotDuration;

	public boolean containsTimeSlot(final LocalDateTime startTime, final LocalDateTime endTime) {
		return bookedTimeSlots.stream()
				.anyMatch(timeSlot -> timeSlot.getStartTime().isEqual(startTime) &&
						timeSlot.getEndTime().isEqual(endTime));
	}

	public void addTimeSlot(final TimeSlot timeSlot) {
		this.bookedTimeSlots.add(timeSlot);
	}
}
