package com.petistaan.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static String validateString(String string) throws IllegalArgumentException {
        if (string.isBlank() || string.isEmpty()) {
            throw new IllegalArgumentException("field can not be empty");
        } else {
            return string.replaceAll("[^a-zA-Z0-9 ]", "");
        }
    }

    public static int validateInteger(String integer) throws IllegalArgumentException {
        try {
            return Integer.parseInt(integer);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please enter a valid integer.");
        }
    }

    public static int validateIntegerWithinRange(String integer, int min, int max) throws IllegalArgumentException {
        int input = validateInteger(integer);
        if (input < min || input > max) {
            throw new IllegalArgumentException("Invalid input. Please enter a number between " + min + " and " + max);
        }
        return input;
    }

    public static char validateCharacterWithOptions(String input, char... options) throws IllegalArgumentException {
        if (input.length() != 1 || !containsChar(options, input.toUpperCase().charAt(0))) {
            throw new IllegalArgumentException("Invalid input. Please enter a valid option.");
        }
        return input.toUpperCase().charAt(0);
    }

    private static boolean containsChar(char[] array, char target) {
        for (char c : array) {
            if (Character.toUpperCase(c) == target) {
                return true;
            }
        }
        return false;
    }

    public static String validateMobileNumber(String mobileNumber) throws IllegalArgumentException {
        if (!mobileNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid mobile number. Must be 10 digits.");
        } else {
            return mobileNumber;
        }
    }

    public static String validateEmail(String email) throws IllegalArgumentException {
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            throw new IllegalArgumentException("Invalid email format. Please provide a valid email.");
        } else {
            return email;
        }
    }

    public static LocalDate validateAndParseDate(String dateStr) throws IllegalArgumentException {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            return LocalDate.parse(dateStr, dateFormat);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please enter the date in dd-MM-yyyy format.");
        }
    }

    public static <T extends Enum<T>> T validateEnumValue(String value, Class<T> enumClass)
            throws IllegalArgumentException {
        try {
            return Enum.valueOf(enumClass, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid value. Please enter one of: " + Arrays.toString(enumClass.getEnumConstants()));
        }
    }
}
