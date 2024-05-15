package com.utopia.api.entities;

public class CategorizedProduct {
    private Long productId;
    private Short categoryId;

    public CategorizedProduct() {
    }

    public CategorizedProduct(Long productId, Short categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Short getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Short categoryId) {
        this.categoryId = categoryId;
    }
}
