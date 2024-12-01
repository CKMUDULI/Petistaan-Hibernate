package com.petistaan;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.petistaan.dto.OwnerDTO;
import com.petistaan.dto.PetDTO;
import com.petistaan.exception.OwnerNotFoundException;
import com.petistaan.exception.OwnerPetCombinationNotFoundException;
import com.petistaan.exception.PetNotFoundException;
import com.petistaan.service.OwnerService;
import com.petistaan.service.PetService;
import com.petistaan.service.impl.OwnerServiceImpl;
import com.petistaan.service.impl.PetServiceImpl;
import com.petistaan.util.InputUtil;

public class App {
	private OwnerService ownerService;
	private PetService petService;

	public App() {
		this.ownerService = new OwnerServiceImpl();
		this.petService = new PetServiceImpl();
	}

	public static void main(String[] args) {
		new App().run();
	}

	private void run() {
		try (Scanner scanner = new Scanner(System.in)) {
			do {
				try {
					System.out.println("Welcome to Petistaan");
					handleMenuOption(scanner);
				} catch (Exception e) {
					// e.printStackTrace();
					System.out.println(e.getMessage());
				}
			} while (InputUtil.wantToContinue(scanner));
		} finally {
			System.out.println("Program exited.");
		}
	}

	private void handleMenuOption(Scanner scanner)
			throws OwnerNotFoundException, OwnerPetCombinationNotFoundException, PetNotFoundException {
		int menuOption = InputUtil.acceptMenuOption(scanner);
		switch (menuOption) {
			case 0 -> System.exit(0);
			case 1 -> handleOwnerAndPetCreation(scanner);
			case 2 -> handleOwnerFetch(scanner);
			case 3 -> handlePetUpdate(scanner);
			case 4 -> handleOwnerDeletion(scanner);
			case 5 -> handlePetFetch(scanner);
			case 6 -> handlePetAddition(scanner);
			case 7 -> handlePetRemoval(scanner);
			case 8 -> handleCoOwnerOperation(scanner);
			case 9 -> handleOwnerFetchUsingHQL(scanner);
			case 10 -> handlePetFetchUsingHQL(scanner);
			case 11 -> handleAllOwnersFetchUsingHQL(scanner);
			case 12 -> handleAllPetsFetchUsingHQL(scanner);
			case 13 -> handleOwnerFetchByInitialsOfFirstName(scanner);
			case 14 -> handleOwnerFetchByPetsBirthDate(scanner);
			case 15 -> handlePetAverageAgeCalculation(scanner);
			case 16 -> handleSpecificDetailsAndPagination(scanner);
			default -> System.out.println("Invalid option entered.");
		}
	}

	private <T> void printDetails(T item, String type) {
		System.out.printf("%s has been fetched successfully.%n%s%n", type, item);
	}

	private <T> void printDetails(List<T> items, String type) {
		if (items.isEmpty()) {
			System.out.printf("No %s found.%n", type);
			return;
		}
		System.out.printf("%d %s has been fetched successfully.%n", items.size(), type);
		items.forEach(System.out::println);
	}

	private void handleOwnerAndPetCreation(Scanner scanner) {
		OwnerDTO ownerDTO = InputUtil.acceptOwnerDetailsToSave(scanner);
		PetDTO petDTO = InputUtil.acceptPetDetailsToSave(scanner);
		ownerDTO.setPetDTOList(List.of(petDTO));
		ownerService.saveOwner(ownerDTO);
		System.out.println("Owner has been saved successfully.");
	}

	private void handleOwnerFetch(Scanner scanner) throws OwnerNotFoundException {
		int ownerId = InputUtil.acceptOwnerIdToOperate(scanner);
		printDetails(ownerService.findOwner(ownerId), "Owner");
		printDetails(ownerService.findOwnerWithPet(ownerId), "Owner with pets");
	}

	private void handlePetUpdate(Scanner scanner) throws OwnerNotFoundException, OwnerPetCombinationNotFoundException {
		int ownerId = InputUtil.acceptOwnerIdToOperate(scanner);
		int petId = InputUtil.acceptPetIdToOperate(scanner);
		String petName = InputUtil.acceptPetDetailsToUpdate(scanner);
		ownerService.updatePetDetails(ownerId, petId, petName);
		System.out.println("Pet details of owner have been updated successfully.");
	}

	private void handleOwnerDeletion(Scanner scanner) throws OwnerNotFoundException {
		ownerService.deleteOwner(InputUtil.acceptOwnerIdToOperate(scanner));
		System.out.println("Owner has been deleted successfully.");
	}

	private void handlePetFetch(Scanner scanner) throws PetNotFoundException {
		int petId = InputUtil.acceptPetIdToOperate(scanner);
		printDetails(petService.findPet(petId), "Pet");
		printDetails(petService.findPetWithOwner(petId), "Pet with owner");
	}

	private void handlePetAddition(Scanner scanner) throws OwnerNotFoundException, PetNotFoundException {
		int petType = InputUtil.acceptPetType(scanner);
		if (petType == 1) {
			int ownerId = InputUtil.acceptOwnerIdToOperate(scanner);
			PetDTO petDTO = InputUtil.acceptPetDetailsToSave(scanner);
			ownerService.addPet(ownerId, petDTO);
		} else if (petType == 2) {
			ownerService.addCoOwner(
					InputUtil.acceptPetIdToOperate(scanner),
					InputUtil.acceptOwnerIdToOperate(scanner));
		}
		System.out.println("Pet has been added successfully.");
	}

	private void handlePetRemoval(Scanner scanner) throws OwnerNotFoundException, OwnerPetCombinationNotFoundException {
		ownerService.removePet(
				InputUtil.acceptOwnerIdToOperate(scanner),
				InputUtil.acceptPetIdToOperate(scanner));
		System.out.println("Pet has been removed successfully.");
	}

	private void handleCoOwnerOperation(Scanner scanner) throws PetNotFoundException, OwnerNotFoundException {
		int coOwnerType = InputUtil.acceptCoOwnerType(scanner);
		int petId = InputUtil.acceptPetIdToOperate(scanner);
		if (coOwnerType == 1) {
			ownerService.addCoOwner(petId, InputUtil.acceptOwnerDetailsToSave(scanner));
		} else if (coOwnerType == 2) {
			ownerService.addCoOwner(petId, InputUtil.acceptOwnerIdToOperate(scanner));
		}
		System.out.println("Co-owner has been added successfully.");
	}

	private void handleOwnerFetchUsingHQL(Scanner scanner) throws OwnerNotFoundException {
		int ownerId = InputUtil.acceptOwnerIdToOperate(scanner);
		boolean lazyLoading = InputUtil.acceptLazyLoadingType(scanner, "Pets");
		printDetails(ownerService.findOwnerUsingHql(ownerId, lazyLoading), "Owner");
	}

	private void handlePetFetchUsingHQL(Scanner scanner) throws PetNotFoundException {
		int petId = InputUtil.acceptPetIdToOperate(scanner);
		boolean lazyLoading = InputUtil.acceptLazyLoadingType(scanner, "Owners");
		printDetails(petService.findPetUsingHql(petId, lazyLoading), "Pet");
	}

	private void handleAllOwnersFetchUsingHQL(Scanner scanner) {
		boolean lazyLoading = InputUtil.acceptLazyLoadingType(scanner, "Pets");
		printDetails(ownerService.findAllOwnersUsingHql(lazyLoading), "Owners");
	}

	private void handleAllPetsFetchUsingHQL(Scanner scanner) {
		boolean lazyLoading = InputUtil.acceptLazyLoadingType(scanner, "Owners");
		printDetails(petService.findAllPetsUsingHql(lazyLoading), "Pets");
	}

	private void handleOwnerFetchByInitialsOfFirstName(Scanner scanner) {
		String ownerInitials = InputUtil.acceptOwnerInitials(scanner);
		printDetails(ownerService.findOwnerByInitialsOfFirstName(ownerInitials), "Owner");
	}

	private void handleOwnerFetchByPetsBirthDate(Scanner scanner) {
		LocalDate startDate = InputUtil.acceptPetBirthDateToOperate(scanner, "Enter start date");
		LocalDate endDate = InputUtil.acceptPetBirthDateToOperate(scanner, "Enter end date");
		printDetails(ownerService.findOwnersByPetsBirthDate(startDate, endDate), "Owner");
	}

	private void handlePetAverageAgeCalculation(Scanner scanner) {
		double averageAge = petService.calculateAveragePetAge();
		System.out.printf("Average age of all pets: %.2f%n", averageAge);
	}

	private void handleSpecificDetailsAndPagination(Scanner scanner) {
		int pageNumber = InputUtil.acceptPageNumber(scanner);
		int pageSize = InputUtil.acceptPageSize(scanner);
		List<Object[]> objects = ownerService.findIdAndFirstNameAndLastNameAndPetName(pageNumber, pageSize);
		System.out.printf("%4s - %-20s - %s%n", "Id", "Full Name", "Pet Names");
		objects.forEach(o -> System.out.printf("%4d - %-20s - %s %n", o[0], o[1] + " " + o[2], o[3]));
	}
}
