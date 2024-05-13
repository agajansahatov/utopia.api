package com.utopia.api.entities;

import com.utopia.api.utilities.Validator;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class User {
    private Long id;
    private String contact;
    private String password;
    private String role_id;
    private String firstname;
    private String lastname;
    private BigDecimal balance;
    private String country;
    private String province;
    private String city;
    private String address;
    private Timestamp authTime;

    public static boolean isValid(User user) {
        if(user == null) {
            return false;
        }
        return Validator.isValidPassword(user.getPassword()) && Validator.isValidContact(user.getContact());
    }

    public User() {
    }

    public User(Long id, String contact, String password, String role_id, String firstname, String lastname, BigDecimal balance, String country, String province, String city, String address, Timestamp authTime) {
        this.id = id;
        this.contact = contact;
        this.password = password;
        this.role_id = role_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.balance = balance;
        this.country = country;
        this.province = province;
        this.city = city;
        this.address = address;
        this.authTime = authTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Timestamp authTime) {
        this.authTime = authTime;
    }
}
