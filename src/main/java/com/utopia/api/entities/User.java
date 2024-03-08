package com.utopia.api.entities;

import com.utopia.api.utilities.Validator;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class User {
    private Long id;
    private String name;
    private String contact;
    private String role;
    private String image;
    private String password;
    private String address;
    private BigDecimal balance;
    private Timestamp authTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (!role.matches("owner|admin|user")) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Timestamp getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Timestamp authTime) {
        this.authTime = authTime;
    }

    public static boolean isValid(User user) {
        if(user == null) {
            return false;
        }
        return Validator.isValidPassword(user.getPassword()) && Validator.isValidContact(user.getContact());
    }
}
