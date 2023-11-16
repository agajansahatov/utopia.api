package com.utopia.api.entities;

public class VisitedProduct {
    private long user;
    private long product;

    public VisitedProduct() {
    }

    public VisitedProduct(long user, long product) {
        this.user = user;
        this.product = product;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }
}
