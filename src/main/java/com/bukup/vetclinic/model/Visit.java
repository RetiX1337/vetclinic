package com.bukup.vetclinic.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

	@ManyToMany(mappedBy = "visits")
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
