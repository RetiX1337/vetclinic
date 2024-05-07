package com.bukup.vetclinic.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@OneToMany(mappedBy = "employee")
	private Set<Visit> visits;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(
			name = "employee_categories",
			joinColumns = { @JoinColumn(name = "employee_id") },
			inverseJoinColumns = { @JoinColumn(name = "category_id") }
	)
	private Set<Category> categories;
}