package com.utopia.api.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ProductInfo {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Timestamp date;
    private String properties;
    private String medias;
    private String categories;
    private Long likesCount;
    private Long visitsCount;
    private Long ordersCount;
    private Long commentsCount;

    public ProductInfo() {
    }

    public ProductInfo(Long id, String title, BigDecimal price, String description, Timestamp date, String properties, String medias, String categories, Long likesCount, Long visitsCount, Long ordersCount, Long commentsCount) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.date = date;
        this.properties = properties;
        this.medias = medias;
        this.categories = categories;
        this.likesCount = likesCount;
        this.visitsCount = visitsCount;
        this.ordersCount = ordersCount;
        this.commentsCount = commentsCount;
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

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getMedias() {
        return medias;
    }

    public void setMedias(String medias) {
        this.medias = medias;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public Long getVisitsCount() {
        return visitsCount;
    }

    public void setVisitsCount(Long visitsCount) {
        this.visitsCount = visitsCount;
    }

    public Long getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(Long ordersCount) {
        this.ordersCount = ordersCount;
    }

    public Long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Long commentsCount) {
        this.commentsCount = commentsCount;
    }
}
