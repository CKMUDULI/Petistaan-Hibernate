package com.petistaan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "wild_pet_table")
public class WildPet extends Pet {
	@Column(name = "place_of_birth", nullable = false)
	private String birthPlace;

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

}
