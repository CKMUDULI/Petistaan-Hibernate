package com.petistaan.dto;

import java.time.LocalDate;
import java.util.Objects;

public class DomesticPetDTO extends PetDTO {
	private LocalDate birthDate;

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		if (Objects.isNull(getOwnerDTOList())) {
			return "DomesticPetDTO [id=" + getId() + ", name=" + getName() + ", gender=" + getGender() + ", birthDate="
					+ birthDate + ", type=" + getType() + "]";
		} else {
			return "DomesticPetDTO [id=" + getId() + ", name=" + getName() + ", gender=" + getGender() + ", birthDate="
					+ birthDate + ", type=" + getType() + ", noOfOwners=" + getOwnerDTOList().size() + ", ownerDTOList="
					+ getOwnerDTOList() + "]";
		}
	}
}
