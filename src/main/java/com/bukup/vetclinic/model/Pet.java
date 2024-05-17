package com.bukup.vetclinic.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pets", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "owner_id" }) })
public class Pet
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "animal_type")
	private String animalType;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private Visitor owner;

	@ManyToMany(mappedBy = "pets", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Set<Visit> visits;

	@Override
	public String toString() {
		return name + " " + animalType;
	}

	@PreRemove
	private void removePetsFromVisits() {
		for (Visit visit : visits) {
			visit.getPets().remove(this);
		}
	}
}
