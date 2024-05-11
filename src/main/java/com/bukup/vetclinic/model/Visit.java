package com.bukup.vetclinic.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "visits")
public class Visit
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(
			name = "visits_pets",
			joinColumns = { @JoinColumn(name = "visit_id") },
			inverseJoinColumns = { @JoinColumn(name = "pet_id") }
	)
	private Set<Pet> pets;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "visitor_id")
	private Visitor visitor;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "result")
	private String result;

	@OneToOne
	@JoinColumn(name = "time_slot_id")
	private TimeSlot timeSlot;
}
