package com.utopia.api.entities;

public class FavouriteProduct {
    private long user;
    private long product;

    public FavouriteProduct() {
    }

    public FavouriteProduct(long user, long product) {
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
