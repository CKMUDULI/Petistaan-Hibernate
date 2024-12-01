package com.petistaan.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.petistaan.config.PropertiesConfig;
import com.petistaan.dto.OwnerDTO;
import com.petistaan.dto.PetDTO;
import com.petistaan.entity.Owner;
import com.petistaan.entity.Pet;
import com.petistaan.exception.OwnerNotFoundException;
import com.petistaan.exception.OwnerPetCombinationNotFoundException;
import com.petistaan.exception.PetNotFoundException;
import com.petistaan.repository.OwnerRepository;
import com.petistaan.repository.PetRepository;
import com.petistaan.repository.impl.OwnerRepositoryImpl;
import com.petistaan.repository.impl.PetRepositoryImpl;
import com.petistaan.service.OwnerService;
import com.petistaan.util.MapperUtil;

public class OwnerServiceImpl implements OwnerService {
	private OwnerRepository ownerRepository;
	private PetRepository petRepository;
	private static final String PET_NOT_FOUND = "pet.not.found";
	private static final String OWNER_NOT_FOUND = "owner.not.found";
	private static final String OWNER_PET_COMBINATION_NOT_FOUND = "owner.pet.combination.not.found";
	private static final PropertiesConfig PROPERTIES_CONFIG = PropertiesConfig.getInstance();

	public OwnerServiceImpl() {
		this.ownerRepository = new OwnerRepositoryImpl();
		this.petRepository = new PetRepositoryImpl();
	}

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		Owner owner = MapperUtil.convertOwnerDtoToEntityWithPet(ownerDTO);
		ownerRepository.saveOwner(owner);
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findOwner(ownerId);
		if (Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
		}
		return MapperUtil.convertOwnerEntityToDtoWithoutPet(owner);
	}

	@Override
	public OwnerDTO findOwnerWithPet(int ownerId) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findOwnerWithPet(ownerId);
		if (Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
		}
		return MapperUtil.convertOwnerEntityToDtoWithPet(owner);
	}

	@Override
	public void updatePetDetails(int ownerId, int petId, String petName)
			throws OwnerNotFoundException, OwnerPetCombinationNotFoundException {
		Owner owner = ownerRepository.findOwnerWithPet(ownerId);
		if (Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
		} else if (owner.getPetList().stream().noneMatch(pet -> pet.getId() == petId)) {
			throw new OwnerPetCombinationNotFoundException(
					String.format(PROPERTIES_CONFIG.getProperty(OWNER_PET_COMBINATION_NOT_FOUND), ownerId, petId));
		} else {
			ownerRepository.updatePetDetails(ownerId, petId, petName);
		}

	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findOwnerWithPet(ownerId);
		if (Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
		}
		ownerRepository.deleteOwner(ownerId);
	}

	@Override
	public void addPet(int ownerId, PetDTO petDTO) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findOwner(ownerId);
		if (Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
		}
		Pet pet = MapperUtil.convertPetDtoToEntityWithoutOwner(petDTO);
		ownerRepository.addPet(ownerId, pet);
	}

	@Override
	public void removePet(int ownerId, int petId) throws OwnerNotFoundException, OwnerPetCombinationNotFoundException {
		Owner owner = ownerRepository.findOwnerWithPet(ownerId);
		if (Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
		} else if (owner.getPetList().stream().noneMatch(pet -> pet.getId() == petId)) {
			throw new OwnerPetCombinationNotFoundException(
					String.format(PROPERTIES_CONFIG.getProperty(OWNER_PET_COMBINATION_NOT_FOUND), ownerId, petId));
		} else {
			ownerRepository.removePet(ownerId, petId);
		}
	}

	@Override
	public void addCoOwner(int petId, OwnerDTO ownerDTO) throws PetNotFoundException {
		Pet pet = petRepository.findPet(petId);
		if (Objects.isNull(pet)) {
			throw new PetNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(PET_NOT_FOUND), petId));
		} else {
			Owner owner = MapperUtil.convertOwnerDtoToEntityWithoutPet(ownerDTO);
			ownerRepository.addCoOwner(petId, owner);
		}
	}

	@Override
	public void addCoOwner(int petId, int ownerId) throws PetNotFoundException, OwnerNotFoundException {
		Owner owner = ownerRepository.findOwner(ownerId);
		if (Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
		}
		Pet pet = petRepository.findPet(petId);
		if (Objects.isNull(pet)) {
			throw new PetNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(PET_NOT_FOUND), petId));
		}
		ownerRepository.addCoOwner(petId, ownerId);
	}

	@Override
	public OwnerDTO findOwnerUsingHql(int ownerId, boolean lazyLoadingPets) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findOwnerUsingHql(ownerId, lazyLoadingPets);
		if (Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
		}
		Function<Owner, OwnerDTO> mapper = lazyLoadingPets ? MapperUtil::convertOwnerEntityToDtoWithPet
				: MapperUtil::convertOwnerEntityToDtoWithoutPet;
		return mapper.apply(owner);
	}

	@Override
	public List<OwnerDTO> findAllOwnersUsingHql(boolean lazyLoading) {
		Function<Owner, OwnerDTO> mapper = lazyLoading ? MapperUtil::convertOwnerEntityToDtoWithPet
				: MapperUtil::convertOwnerEntityToDtoWithoutPet;
		return ownerRepository.findAllOwnersUsingHql(lazyLoading).stream().map(mapper).toList();
	}

	@Override
	public List<OwnerDTO> findOwnerByInitialsOfFirstName(String ownerInitials) {
		return ownerRepository.findOwnerByInitialsOfFirstName(ownerInitials).stream().map(MapperUtil::convertOwnerEntityToDtoWithoutPet).toList();
	}

	@Override
	public List<OwnerDTO> findOwnersByPetsBirthDate(LocalDate startDate, LocalDate endDate) {
		return ownerRepository.findOwnersByPetsBirthDate(startDate, endDate).stream().map(MapperUtil::convertOwnerEntityToDtoWithoutPet).toList();
	}

	@Override
	public List<Object[]> findIdAndFirstNameAndLastNameAndPetName(int pageNumber, int pageSize) {
		return ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageNumber, pageSize);
	}

}
