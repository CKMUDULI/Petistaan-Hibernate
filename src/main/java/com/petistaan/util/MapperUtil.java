package com.petistaan.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.petistaan.dto.DomesticPetDTO;
import com.petistaan.dto.OwnerDTO;
import com.petistaan.dto.PetDTO;
import com.petistaan.dto.WildPetDTO;
import com.petistaan.entity.DomesticPet;
import com.petistaan.entity.Owner;
import com.petistaan.entity.Pet;
import com.petistaan.entity.WildPet;

public class MapperUtil {
	private MapperUtil() {
	}

	public static Owner convertOwnerDtoToEntityWithPet(OwnerDTO ownerDTO) {
		Owner owner = new Owner();
		owner.setFirstName(ownerDTO.getFirstName());
		owner.setLastName(ownerDTO.getLastName());
		owner.setGender(ownerDTO.getGender());
		owner.setCity(ownerDTO.getCity());
		owner.setState(ownerDTO.getState());
		owner.setMobileNumber(ownerDTO.getMobileNumber());
		owner.setEmailId(ownerDTO.getEmailId());
		Set<Pet> petList = ownerDTO.getPetDTOList().stream().map(MapperUtil::convertPetDtoToEntityWithoutOwner)
				.collect(Collectors.toSet());
		owner.setPetList(petList);
		return owner;
	}

	public static Pet convertPetDtoToEntityWithoutOwner(PetDTO petDTO) {
		Pet pet = null;
		if (petDTO instanceof DomesticPetDTO domesticPetDTO) {
			pet = new DomesticPet();
			((DomesticPet) pet).setBirthDate(domesticPetDTO.getBirthDate());
		} else if (petDTO instanceof WildPetDTO wildPetDTO) {
			pet = new WildPet();
			((WildPet) pet).setBirthPlace(wildPetDTO.getBirthPlace());
		} else {
			throw new IllegalArgumentException("Unsupported petDto instance: " + petDTO.getClass().getName());
		}
		pet.setName(petDTO.getName());
		pet.setGender(petDTO.getGender());
		pet.setType(petDTO.getType());
		return pet;
	}

	public static OwnerDTO convertOwnerEntityToDtoWithoutPet(Owner owner) {
		OwnerDTO ownerDTO = new OwnerDTO();
		ownerDTO.setId(owner.getId());
		ownerDTO.setFirstName(owner.getFirstName());
		ownerDTO.setLastName(owner.getLastName());
		ownerDTO.setGender(owner.getGender());
		ownerDTO.setCity(owner.getCity());
		ownerDTO.setState(owner.getState());
		ownerDTO.setMobileNumber(owner.getMobileNumber());
		ownerDTO.setEmailId(owner.getEmailId());
		return ownerDTO;
	}

	public static OwnerDTO convertOwnerEntityToDtoWithPet(Owner owner) {
		OwnerDTO ownerDTO = new OwnerDTO();
		ownerDTO.setId(owner.getId());
		ownerDTO.setFirstName(owner.getFirstName());
		ownerDTO.setLastName(owner.getLastName());
		ownerDTO.setGender(owner.getGender());
		ownerDTO.setCity(owner.getCity());
		ownerDTO.setState(owner.getState());
		ownerDTO.setMobileNumber(owner.getMobileNumber());
		ownerDTO.setEmailId(owner.getEmailId());
		List<PetDTO> petDTOList = owner.getPetList().stream().map(MapperUtil::convertPetEntityToDtoWithoutOwner)
				.toList();
		ownerDTO.setPetDTOList(petDTOList);
		return ownerDTO;
	}

	public static PetDTO convertPetEntityToDtoWithoutOwner(Pet pet) {
		PetDTO petDTO = null;
		if (pet instanceof DomesticPet domesticPet) {
			petDTO = new DomesticPetDTO();
			((DomesticPetDTO) petDTO).setBirthDate(domesticPet.getBirthDate());
		} else if (pet instanceof WildPet wildPet) {
			petDTO = new WildPetDTO();
			((WildPetDTO) petDTO).setBirthPlace(wildPet.getBirthPlace());
		} else {
			throw new IllegalArgumentException("Unsupported pet instance: " + pet.getClass().getName());
		}
		petDTO.setId(pet.getId());
		petDTO.setName(pet.getName());
		petDTO.setGender(pet.getGender());
		petDTO.setType(pet.getType());
		return petDTO;
	}

	public static PetDTO convertPetEntityToDtoWithOwner(Pet pet) {
		PetDTO petDTO = null;
		if (pet instanceof DomesticPet domesticPet) {
			petDTO = new DomesticPetDTO();
			((DomesticPetDTO) petDTO).setBirthDate(domesticPet.getBirthDate());
		} else if (pet instanceof WildPet wildPet) {
			petDTO = new WildPetDTO();
			((WildPetDTO) petDTO).setBirthPlace(wildPet.getBirthPlace());
		} else {
			throw new IllegalArgumentException("Unsupported pet instance: " + pet.getClass().getName());
		}
		petDTO.setId(pet.getId());
		petDTO.setName(pet.getName());
		petDTO.setGender(pet.getGender());
		petDTO.setType(pet.getType());
		List<OwnerDTO> ownerDTOList = pet.getOwnerList().stream().map(MapperUtil::convertOwnerEntityToDtoWithoutPet)
				.toList();
		petDTO.setOwnerDTOList(ownerDTOList);
		return petDTO;
	}

	public static Pet convertPetDtoToEntityWithOwner(PetDTO petDTO) {
		Pet pet = null;
		if (petDTO instanceof DomesticPetDTO domesticPetDTO) {
			pet = new DomesticPet();
			((DomesticPet) pet).setBirthDate(domesticPetDTO.getBirthDate());
		} else if (petDTO instanceof WildPetDTO wildPetDTO) {
			pet = new WildPet();
			((WildPet) pet).setBirthPlace(wildPetDTO.getBirthPlace());
		} else {
			throw new IllegalArgumentException("Unsupported petDto instance: " + petDTO.getClass().getName());
		}
		pet.setName(petDTO.getName());
		pet.setGender(petDTO.getGender());
		pet.setType(petDTO.getType());
		Set<Owner> ownerList = petDTO.getOwnerDTOList().stream().map(MapperUtil::convertOwnerDtoToEntityWithoutPet)
				.collect(Collectors.toSet());
		pet.setOwnerList(ownerList);
		return pet;
	}

	public static Owner convertOwnerDtoToEntityWithoutPet(OwnerDTO ownerDTO) {
		Owner owner = new Owner();
		owner.setFirstName(ownerDTO.getFirstName());
		owner.setLastName(ownerDTO.getLastName());
		owner.setGender(ownerDTO.getGender());
		owner.setCity(ownerDTO.getCity());
		owner.setState(ownerDTO.getState());
		owner.setEmailId(ownerDTO.getEmailId());
		owner.setMobileNumber(ownerDTO.getMobileNumber());
		return owner;
	}
}
