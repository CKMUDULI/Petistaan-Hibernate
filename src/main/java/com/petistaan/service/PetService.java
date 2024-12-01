package com.petistaan.service;

import java.util.List;

import com.petistaan.dto.PetDTO;
import com.petistaan.exception.PetNotFoundException;

public interface PetService {
    PetDTO findPet(int petId) throws PetNotFoundException;

    PetDTO findPetWithOwner(int petId) throws PetNotFoundException;

    PetDTO findPetUsingHql(int petId, boolean lazyLoadingOwners) throws PetNotFoundException;

    List<PetDTO> findAllPetsUsingHql(boolean lazyLoading);

    double calculateAveragePetAge();
}
