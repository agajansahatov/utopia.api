package com.utopia.api.dto;

import com.utopia.api.entities.User;
import com.utopia.api.utilities.Validator;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserResponseDTO {
    private final Long id;
    private final String contact;
    private final String firstname;
    private final String lastname;
    private final BigDecimal balance;
    private final String country;
    private final String province;
    private final String city;
    private final String address;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.contact = maskContact(user.getContact());
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.balance = user.getBalance();
        this.country = user.getCountry();
        this.province = user.getProvince();
        this.city = user.getCity();
        this.address = user.getAddress();
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

    public String getContact() {
        return contact;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }
}
