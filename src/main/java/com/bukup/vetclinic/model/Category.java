package com.bukup.vetclinic.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "type", unique = true, nullable = false)
	private String type;

	@ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
	private Set<Employee> employees;

	@OneToMany(mappedBy = "category")
	private Set<ServiceType> serviceTypes;
}
