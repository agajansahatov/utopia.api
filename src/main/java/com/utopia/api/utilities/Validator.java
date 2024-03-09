package com.utopia.api.utilities;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Validator {
    private boolean isValid;
    private String message;

    public Validator(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static boolean isValidPhoneNumber(String str) {
        Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+?[0-9]{1,}$");
        return str != null && PHONE_NUMBER_PATTERN.matcher(str).matches();
    }

    public static boolean isValidEmail(String email) {
        if(email == null) {
            return false;
        }
        Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        return EMAIL_PATTERN.matcher(email).matches();
    }
    public static boolean isValidPassword(String password) {
        if(password == null) {
            return false;
        }
        return password.length() >= 5;
    }

    public static boolean isValidContact(String contact) {
        if(contact == null) {
            return false;
        }

        if (isValidPhoneNumber(contact)){
            return contact.length() >= 7;
        }
        return isValidEmail(contact);
    }

    public static Validator validateFile(MultipartFile file, String type, String[] allowedExtensions) {
        if (file == null || file.isEmpty()) {
            return new Validator(false, type.toUpperCase() + " is required.");
        }
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return new Validator(false, "Original " + type + " name cannot be null.");
        }
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (fileExtension.isEmpty()) {
            return new Validator(false, "Invalid filename or extension.");
        }
        boolean isValidExtension = Arrays.stream(allowedExtensions).anyMatch(ext -> ext.equalsIgnoreCase(fileExtension));
        if (!isValidExtension) {
            return new Validator(false, "Only " + type + "s are allowed.");
        }
        return new Validator(true, "Valid file.");
    }
}
