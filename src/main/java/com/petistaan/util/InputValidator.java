package com.petistaan.util;

import java.util.Scanner;

public class InputValidator {
	private InputValidator() {
	}

	@FunctionalInterface
	public interface Validator<R> {
		R validate(String input);
	}

	public static <R> R getValidatedInput(Scanner scanner, String prompt, Validator<R> validator) {
		while (true) {
			try {
				System.out.println(prompt);
				System.out.print(">>> ");
				String input = scanner.next();
				return validator.validate(input);
			} catch (IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}
}