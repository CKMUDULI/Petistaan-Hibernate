package com.petistaan.repository;

import java.util.List;

import com.petistaan.entity.Pet;

public interface PetRepository {
    Pet findPet(int petId);

    Pet findPetWithOwner(int petId);

    Pet findPetUsingHql(int petId, boolean lazyLoadingOwners);

    List<Pet> findAllPetsUsingHql(boolean lazyLoading);

    double calculateAveragePetAge();
}
