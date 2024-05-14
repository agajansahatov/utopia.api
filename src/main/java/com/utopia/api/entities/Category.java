package com.utopia.api.entities;

public class Category {
    private Short id;
    private String name;

    public Category() {
    }

    public Category(Short id, String name) {
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
