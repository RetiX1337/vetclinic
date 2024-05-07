package com.bukup.vetclinic.model;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pets")
public class Pet
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "date_of_birth")
	private LocalDateTime dateOfBirth;

	@Column(name = "animal_type")
	private String animalType;

	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private Visitor owner;

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(
			name = "pets_visits",
			joinColumns = { @JoinColumn(name = "pet_id") },
			inverseJoinColumns = { @JoinColumn(name = "visit_id") }
	)
	private Set<Visit> visits;
}
