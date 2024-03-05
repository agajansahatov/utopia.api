package com.utopia.api.dto;

import com.utopia.api.entities.User;

import java.math.BigDecimal;

public class UserResponseDTO {
    private Long id;
    private String name;
    private String contact;
    private String image;
    private String address;
    private BigDecimal balance;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.contact = user.getContact();
        this.image = user.getImage();
        this.address = user.getAddress();
        this.balance = user.getBalance();
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
