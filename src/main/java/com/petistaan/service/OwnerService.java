package com.petistaan.service;

import java.time.LocalDate;
import java.util.List;

import com.petistaan.dto.OwnerDTO;
import com.petistaan.dto.PetDTO;
import com.petistaan.exception.OwnerNotFoundException;
import com.petistaan.exception.OwnerPetCombinationNotFoundException;
import com.petistaan.exception.PetNotFoundException;

public interface OwnerService {
	void saveOwner(OwnerDTO ownerDTO);

	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

	OwnerDTO findOwnerWithPet(int ownerId) throws OwnerNotFoundException;

	void updatePetDetails(int ownerId, int petId, String petName)
			throws OwnerNotFoundException, OwnerPetCombinationNotFoundException;

	void deleteOwner(int ownerId) throws OwnerNotFoundException;

	void addPet(int ownerId, PetDTO petDTO) throws OwnerNotFoundException;

	void removePet(int ownerId, int petId) throws OwnerNotFoundException, OwnerPetCombinationNotFoundException;

	void addCoOwner(int petId, OwnerDTO ownerDTO) throws PetNotFoundException;

	void addCoOwner(int petId, int ownerId) throws PetNotFoundException, OwnerNotFoundException;

	OwnerDTO findOwnerUsingHql(int ownerId, boolean lazyLoadingPets) throws OwnerNotFoundException;

	List<OwnerDTO> findAllOwnersUsingHql(boolean lazyLoading);

    List<OwnerDTO> findOwnerByInitialsOfFirstName(String ownerInitials);

	List<OwnerDTO> findOwnersByPetsBirthDate(LocalDate startDate, LocalDate endDate);

	List<Object[]> findIdAndFirstNameAndLastNameAndPetName(int pageNumber, int pageSize);
}
