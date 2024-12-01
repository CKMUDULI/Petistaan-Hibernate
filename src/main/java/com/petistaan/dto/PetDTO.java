package com.petistaan.dto;

import java.util.List;
import java.util.Objects;

import com.petistaan.enums.Gender;
import com.petistaan.enums.PetType;

public class PetDTO {
	private int id;
	private String name;
	private Gender gender;
	private PetType type;
	private List<OwnerDTO> ownerDTOList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public List<OwnerDTO> getOwnerDTOList() {
		return ownerDTOList;
	}

	public void setOwnerDTOList(List<OwnerDTO> ownerDTOList) {
		this.ownerDTOList = ownerDTOList;
	}

	@Override
	public String toString() {
		if (Objects.isNull(ownerDTOList)) {
			return "PetDTO [id=" + id + ", name=" + name + ", gender=" + gender + ", type=" + type + "]";
		} else {
			return "PetDTO [id=" + id + ", name=" + name + ", gender=" + gender + ", type=" + type + ", noOfOwners="
					+ ownerDTOList.size() + ", ownerDTOList=" + ownerDTOList + "]";
		}
	}
}
