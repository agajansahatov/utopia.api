package com.utopia.api.entities;

import com.utopia.api.utilities.Validator;

import java.math.BigDecimal;

public class User {
    private Long id;
    private String name;
    private String contact;
    private String image;
    private String password;
    private String address;
    private BigDecimal balance;

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

    public static boolean isValid(User user) {
        if(user == null) {
            return false;
        }
        if(user.getContact() == null) {
            return false;
        }
        if(user.getPassword() == null) {
            return false;
        }
        if(!Validator.isValidContact(user.getContact())){
            return false;
        }
        if(user.getPassword().length() < 5){
            return false;
        }
        return true;
    }
}
