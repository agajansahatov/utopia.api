package com.utopia.api.entities;

public class Media {
    private int id;
    private long product_id;
    private String name;
    private boolean is_main;

    public Media() {
    }

    public Media(int id, long product_id, String name, boolean is_main) {
        this.id = id;
        this.product_id = product_id;
        this.name = name;
        this.is_main = is_main;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_main() {
        return is_main;
    }

    public void setIs_main(boolean is_main) {
        this.is_main = is_main;
    }
}
