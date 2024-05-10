package com.bukup.vetclinic.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
	private List<ScheduleDay> scheduleDays;
}
