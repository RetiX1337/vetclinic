package com.bukup.vetclinic.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "schedule_days")
public class ScheduleDay
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "date")
	private LocalDate date;

	@OneToMany(mappedBy = "scheduleDay", cascade = CascadeType.ALL)
	private List<TimeSlot> timeSlots;

	@ManyToOne
	private Schedule schedule;
}
