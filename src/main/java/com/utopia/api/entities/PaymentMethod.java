package com.utopia.api.entities;

public class PaymentMethod {
    private Short id;
    private String name;

    public PaymentMethod() {
    }

    public PaymentMethod(Short id, String name) {
        this.id = id;
        this.name = name;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
