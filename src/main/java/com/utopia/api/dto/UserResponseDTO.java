package com.utopia.api.dto;

import com.utopia.api.entities.User;
import com.utopia.api.utilities.Validator;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserResponseDTO {
    private final Long id;
    private final String name;
    private final String contact;
    private final String image;
    private final String address;
    private final BigDecimal balance;
    private final String password;
    private final String role;
    private final Timestamp authTime;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.image = user.getImage();
        this.address = user.getAddress();
        this.balance = user.getBalance();
        this.password = "*****";
        this.contact = maskContact(user.getContact());
        this.role = user.getRole();
        this.authTime = user.getAuthTime();
    }

    private String maskContact(String contact) {
        if(contact == null) {
            return null;
        }
        if(Validator.isValidEmail(contact)) {
            return maskEmail(contact);
        }
        return maskPhoneNumber(contact);
    }

    private String maskEmail(String email) {
        if (email == null || email.isEmpty()) {
            return email;
        }

        int atIndex = email.indexOf("@");

        if (atIndex <= 1) {
            return email;
        }

        String namePart = email.substring(0, atIndex);
        String maskedName = namePart.charAt(0) + "***" + namePart.charAt(namePart.length() - 1);

        return maskedName + email.substring(atIndex);
    }

    private String maskPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return phoneNumber; // Return original phone number if it's null or empty
        }

        int length = phoneNumber.length();
        if (length <= 4) {
            return phoneNumber; // Return original number if it has 4 or fewer characters
        }

        String maskedChars = "*".repeat(length - 4); // Replace characters except the last 4 with asterisks
        String lastFourChars = phoneNumber.substring(length - 4); // Get the last four characters

        return maskedChars + lastFourChars; // Combine masked characters with the last four characters
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getImage() {
        return image;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Timestamp getAuthTime() {
        return authTime;
    }
}
