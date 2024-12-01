package com.petistaan.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.petistaan.config.PropertiesConfig;
import com.petistaan.dto.PetDTO;
import com.petistaan.entity.Pet;
import com.petistaan.exception.PetNotFoundException;
import com.petistaan.repository.PetRepository;
import com.petistaan.repository.impl.PetRepositoryImpl;
import com.petistaan.service.PetService;
import com.petistaan.util.MapperUtil;

public class PetServiceImpl implements PetService {
	private PetRepository petRepository;
	private static final String PET_NOT_FOUND = "pet.not.found";
	private static final PropertiesConfig PROPERTIES_CONFIG = PropertiesConfig.getInstance();

	public PetServiceImpl() {
		this.petRepository = new PetRepositoryImpl();
	}

	@Override
	public PetDTO findPet(int petId) throws PetNotFoundException {
		Pet pet = petRepository.findPet(petId);
		if (Objects.isNull(pet)) {
			throw new PetNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(PET_NOT_FOUND), petId));
		}
		return MapperUtil.convertPetEntityToDtoWithoutOwner(pet);
	}

	@Override
	public PetDTO findPetWithOwner(int petId) throws PetNotFoundException {
		Pet pet = petRepository.findPetWithOwner(petId);
		if (Objects.isNull(pet)) {
			throw new PetNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(PET_NOT_FOUND), petId));
		}
		return MapperUtil.convertPetEntityToDtoWithOwner(pet);
	}

	@Override
	public PetDTO findPetUsingHql(int petId, boolean lazyLoadingOwners) throws PetNotFoundException {
		Pet pet = petRepository.findPetUsingHql(petId, lazyLoadingOwners);
		if (Objects.isNull(pet)) {
			throw new PetNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(PET_NOT_FOUND), petId));
		}
		Function<Pet, PetDTO> mapper = lazyLoadingOwners ? MapperUtil::convertPetEntityToDtoWithOwner
				: MapperUtil::convertPetEntityToDtoWithoutOwner;
		return mapper.apply(pet);
	}

	@Override
	public List<PetDTO> findAllPetsUsingHql(boolean lazyLoading) {
		Function<Pet, PetDTO> mapper = lazyLoading ? MapperUtil::convertPetEntityToDtoWithOwner
				: MapperUtil::convertPetEntityToDtoWithoutOwner;
		return petRepository.findAllPetsUsingHql(lazyLoading).stream().map(mapper).toList();
	}

	@Override
	public double calculateAveragePetAge() {
		return petRepository.calculateAveragePetAge();
	}	
}
