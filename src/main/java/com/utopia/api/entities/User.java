package com.utopia.api.entities;

public class User {
    private long id;
    private String name;
    private String contact;
    private String image;
    private String password;
    private String address;
    private String balance;

    public User() {
    }

    public User(long id, String name, String contact, String image, String password, String address, String balance) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.image = image;
        this.password = password;
        this.address = address;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
