package com.bukup.vetclinic.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "time_slots")
public class TimeSlot
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "visit_id")
	private Visit visit;

	@Column(name = "start_time")
	private LocalDateTime start_time;

	@Column(name = "end_time")
	private LocalDateTime end_time;

	@ManyToOne
	@JoinColumn(name = "schedule_day_id")
	private ScheduleDay scheduleDay;
}
