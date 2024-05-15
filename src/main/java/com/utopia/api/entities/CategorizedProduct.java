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
}
