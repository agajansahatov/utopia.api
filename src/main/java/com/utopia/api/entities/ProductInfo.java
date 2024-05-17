package com.utopia.api.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class ProductInfo {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Timestamp date;
    private Map<String, Object> properties;
    private List<Media> medias; // Add this field
    private List<Long> categories; // Add this field
    private Long likesCount;
    private Long visitsCount;
    private Long ordersCount;
    private Long commentsCount;

    public ProductInfo() {
    }

    public ProductInfo(Long id, String title, BigDecimal price, String description, Timestamp date, Map<String, Object> properties, List<Media> medias, List<Long> categories, Long likesCount, Long visitsCount, Long ordersCount, Long commentsCount) {
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

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
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
