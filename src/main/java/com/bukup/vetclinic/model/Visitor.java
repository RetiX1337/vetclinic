package com.bukup.vetclinic.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "visitors")
public class Visitor
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<Pet> pets;

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(
			name = "visitors_visits",
			joinColumns = { @JoinColumn(name = "visitor_id") },
			inverseJoinColumns = { @JoinColumn(name = "visit_id") }
	)
	private Set<Visit> visits;
}
