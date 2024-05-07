package com.bukup.vetclinic.model;

import java.sql.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
	private Date date;

	@OneToMany(mappedBy = "scheduleDay", cascade = CascadeType.ALL)
	private Set<TimeSlot> timeSlots;

	@ManyToOne
	private Schedule schedule;
}
