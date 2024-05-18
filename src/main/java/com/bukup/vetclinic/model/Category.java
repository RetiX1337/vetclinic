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

	@ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH })
	private Set<Employee> employees;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Set<ServiceType> serviceTypes;

	@Override
	public String toString() {
		return type;
	}

	@PreRemove
	private void handlePreRemove() {
		removeCategoryFromEmployees();
	}

	private void removeCategoryFromEmployees() {
		for (Employee employee : employees) {
			employee.getCategories().remove(this);
		}
	}
}
