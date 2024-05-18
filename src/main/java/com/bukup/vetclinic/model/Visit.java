package com.bukup.vetclinic.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "visits")
public class Visit implements Comparable<Visit>
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(
			name = "visits_pets",
			joinColumns = { @JoinColumn(name = "visit_id") },
			inverseJoinColumns = { @JoinColumn(name = "pet_id") }
	)
	private Set<Pet> pets;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visitor_id")
	private Visitor visitor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_type_id")
	private ServiceType serviceType;

	@Column(name = "result")
	private String result;

	@OneToOne
	@JoinColumn(name = "time_slot_id")
	private TimeSlot timeSlot;

	@Override
	public int compareTo(Visit other) {
		return this.timeSlot.getStartTime().compareTo(other.timeSlot.getStartTime());
	}
}
