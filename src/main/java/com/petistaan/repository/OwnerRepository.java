package com.petistaan.repository;

import java.time.LocalDate;
import java.util.List;

import com.petistaan.entity.Owner;
import com.petistaan.entity.Pet;

public interface OwnerRepository {
	void saveOwner(Owner owner);

	Owner findOwner(int ownerId);

	Owner findOwnerWithPet(int ownerId);

	void updatePetDetails(int ownerId, int petId, String petName);

	void deleteOwner(int ownerId);

	void addPet(int ownerId, Pet pet);

	void removePet(int ownerId, int petId);

	void addCoOwner(int petId, Owner owner);

	void addCoOwner(int petId, int ownerId);

	Owner findOwnerUsingHql(int ownerId, boolean lazyLoadingPets);

	List<Owner> findAllOwnersUsingHql(boolean lazyLoading);

	List<Owner> findOwnerByInitialsOfFirstName(String ownerInitials);

	List<Owner> findOwnersByPetsBirthDate(LocalDate startDate, LocalDate endDate);

	List<Object[]> findIdAndFirstNameAndLastNameAndPetName(int pageNumber, int pageSize);
}
