package com.petistaan.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

import com.petistaan.dto.DomesticPetDTO;
import com.petistaan.dto.OwnerDTO;
import com.petistaan.dto.PetDTO;
import com.petistaan.dto.WildPetDTO;
import com.petistaan.enums.Gender;
import com.petistaan.enums.PetType;
import com.petistaan.exception.InternalServiceException;

public class InputUtil {
	private InputUtil() {
	}

	public static boolean wantToContinue(Scanner scanner) {
		char choice = InputValidator.getValidatedInput(scanner, "Press Y to continue and N to exit.",
				input -> ValidationUtil.validateCharacterWithOptions(input, 'Y', 'N'));
		return choice == 'Y';
	}

	public static int acceptMenuOption(Scanner scanner) {
		String[] menuOptions = { "Press 1 to add new owner.", "Press 2 to fetch owner details.",
				"Press 3 to update pet details of owner.", "Press 4 to delete owner details.",
				"Press 5 to fetch pet details.", "Press 6 to add new pet to existing owner.",
				"Press 7 to remove pet details.", "Press 8 to add co-owner.",
				"Press 9 to fetch owner details (using HQL)", "Press 10 to fetch pet details (using HQL)",
				"Press 11 to fetch all owners (using HQL)", "press 12 to fetch all pets (using HQL)",
				"Press 13 to fetch owner by initials of first name of owner. (using HQL)",
				"Press 14 to fetch owner details whose pets born within a time period. (using HQL)",
				"Press 15 to find average age of pet.(using HQL)", "Press 16 to find specific details using pagination.(using HQL)",
				"Press 0 to exit." };
		return InputValidator.getValidatedInput(scanner, String.join("\n", menuOptions),
				input -> ValidationUtil.validateIntegerWithinRange(input, 0, menuOptions.length - 1));
	}

	public static OwnerDTO acceptOwnerDetailsToSave(Scanner scanner) {
		OwnerDTO ownerDTO = new OwnerDTO();
		ownerDTO.setFirstName(InputValidator.getValidatedInput(scanner, "Enter first name of owner:",
				ValidationUtil::validateString));
		ownerDTO.setLastName(
				InputValidator.getValidatedInput(scanner, "Enter last name of owner:", ValidationUtil::validateString));
		ownerDTO.setGender(InputValidator.getValidatedInput(scanner,
				"Enter gender of owner (options: " + Arrays.asList(Gender.values()) + "):",
				input -> ValidationUtil.validateEnumValue(input, Gender.class)));
		ownerDTO.setCity(
				InputValidator.getValidatedInput(scanner, "Enter city of owner:", ValidationUtil::validateString));
		ownerDTO.setState(
				InputValidator.getValidatedInput(scanner, "Enter state of owner:", ValidationUtil::validateString));
		ownerDTO.setMobileNumber(InputValidator.getValidatedInput(scanner, "Enter mobile number of owner (10 digits):",
				ValidationUtil::validateMobileNumber));
		ownerDTO.setEmailId(
				InputValidator.getValidatedInput(scanner, "Enter email id of owner:", ValidationUtil::validateEmail));
		return ownerDTO;
	}

	public static PetDTO acceptPetDetailsToSave(Scanner scanner) {
		String petName = InputValidator.getValidatedInput(scanner, "Enter name of pet:",
				ValidationUtil::validateString);
		char choice = acceptPetCategoryToOperate(scanner);
		LocalDate petDateOfBirth = null;
		String petPlaceOfBirth = null;
		if (choice == 'D') {
			petDateOfBirth = InputValidator.getValidatedInput(scanner, "Enter date of birth of pet (dd-MM-yyyy):",
					ValidationUtil::validateAndParseDate);
		} else if (choice == 'W') {
			petPlaceOfBirth = InputValidator.getValidatedInput(scanner, "Enter place of birth of pet:",
					ValidationUtil::validateString);
		} else {
			throw new InternalServiceException("Unsupported pet category: " + choice);
		}
		Gender petGender = InputValidator.getValidatedInput(scanner,
				"Enter gender of pet (options: " + Arrays.asList(Gender.values()) + "):",
				input -> ValidationUtil.validateEnumValue(input, Gender.class));
		PetType petType = InputValidator.getValidatedInput(scanner,
				"Enter type of pet (options: " + Arrays.asList(PetType.values()) + "):",
				input -> ValidationUtil.validateEnumValue(input, PetType.class));
		try {
			PetDTO petDTO;
			if (choice == 'D') {
				petDTO = new DomesticPetDTO();
				((DomesticPetDTO) petDTO).setBirthDate(petDateOfBirth);
			} else if (choice == 'W') {
				petDTO = new WildPetDTO();
				((WildPetDTO) petDTO).setBirthPlace(petPlaceOfBirth);
			} else {
				throw new InternalServiceException("Unsupported pet category: " + choice);
			}
			petDTO.setName(petName);
			petDTO.setGender(petGender);
			petDTO.setType(petType);
			return petDTO;
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			return acceptPetDetailsToSave(scanner);
		}
	}

	public static char acceptPetCategoryToOperate(Scanner scanner) {
		return InputValidator.getValidatedInput(scanner, "Press D for domestic pet and W for wild pet:",
				input -> ValidationUtil.validateCharacterWithOptions(input, 'D', 'W'));
	}

	public static int acceptOwnerIdToOperate(Scanner scanner) {
		return InputValidator.getValidatedInput(scanner, "Enter id of owner:", ValidationUtil::validateInteger);
	}

	public static String acceptPetDetailsToUpdate(Scanner scanner) {
		return InputValidator.getValidatedInput(scanner, "Enter updated name of pet:", ValidationUtil::validateString);
	}

	public static int acceptPetIdToOperate(Scanner scanner) {
		return InputValidator.getValidatedInput(scanner, "Enter id of pet:", ValidationUtil::validateInteger);
	}

	public static int acceptCoOwnerType(Scanner scanner) {
		return InputValidator.getValidatedInput(scanner, "Press 1 for new co-owner and 2 for existing co-owner:",
				input -> ValidationUtil.validateIntegerWithinRange(input, 1, 2));
	}

	public static int acceptPetType(Scanner scanner) {
		return InputValidator.getValidatedInput(scanner, "Press 1 for new pet and 2 for existing pet:",
				input -> ValidationUtil.validateIntegerWithinRange(input, 1, 2));
	}

	public static boolean acceptLazyLoadingType(Scanner scanner, String entity) {
		return InputValidator.getValidatedInput(scanner,
				"Press 1 to fetch with " + entity + " and 2 to fetch without " + entity + ":",
				input -> ValidationUtil.validateIntegerWithinRange(input, 1, 2)) == 1;
	}

	public static String acceptOwnerInitials(Scanner scanner) {
		return InputValidator.getValidatedInput(scanner, "Enter initials of owner (first name):",
				ValidationUtil::validateString);
	}

	public static LocalDate acceptPetBirthDateToOperate(Scanner scanner, String prompt) {
		return InputValidator.getValidatedInput(scanner, prompt + " (dd-MM-yyyy):", ValidationUtil::validateAndParseDate);
	}

	public static int acceptPageNumber(Scanner scanner) {
		return InputValidator.getValidatedInput(scanner, "Enter page number:", ValidationUtil::validateInteger);
	}

    public static int acceptPageSize(Scanner scanner) {
        return InputValidator.getValidatedInput(scanner, "Enter page size:", ValidationUtil::validateInteger);
    }
}
