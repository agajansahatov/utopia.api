package com.utopia.api.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

// This product entity is for client side
public class Product {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Timestamp date;
    private String media;

    public Product() {
    }

    public Product(Long id, String title, BigDecimal price, String description, Timestamp date, String media) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.date = date;
        this.media = media;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
