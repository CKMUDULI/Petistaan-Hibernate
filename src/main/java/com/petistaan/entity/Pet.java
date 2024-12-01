package com.petistaan.entity;

import java.util.HashSet;
import java.util.Set;

import com.petistaan.enums.Gender;
import com.petistaan.enums.PetType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pet_table")
@Inheritance(strategy = InheritanceType.JOINED)
public class Pet extends Base {
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Gender gender;
	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private PetType type;
	@ManyToMany(mappedBy = "petList")
	private Set<Owner> ownerList = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public PetType getType() {
		return type;
	}

	public void setType(PetType type) {
		this.type = type;
	}

	public Set<Owner> getOwnerList() {
		return ownerList;
	}

	public void setOwnerList(Set<Owner> ownerList) {
		this.ownerList = ownerList;
	}

}
