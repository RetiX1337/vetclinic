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
	@Column(name = "user_id", nullable = false)
	private Long userId;

	@OneToOne(targetEntity = User.class)
	@MapsId
	private User user;

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