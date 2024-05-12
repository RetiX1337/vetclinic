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
	@Column(name = "user_id", nullable = false)
	private Long userId;

	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
	@MapsId
	private User user;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private Set<Pet> pets;

	@OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL)
	private Set<Visit> visits;
}
