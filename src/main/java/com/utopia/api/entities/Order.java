package com.utopia.api.entities;

import java.sql.Timestamp;

public class Order {
    private Long id;
    private Long userId;
    private Long productId;
    private int quantity;
    private Timestamp order_date;
    private Timestamp shipped_date;
    private Short shipper_id;
    private Short payment_method_id;
    private Short status_id;

    public Order() {
    }

    public Order(Long id, Long userId, Long productId, int quantity, Timestamp order_date, Timestamp shipped_date, Short shipper_id, Short payment_method_id, Short status_id) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.order_date = order_date;
        this.shipped_date = shipped_date;
        this.shipper_id = shipper_id;
        this.payment_method_id = payment_method_id;
        this.status_id = status_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Timestamp order_date) {
        this.order_date = order_date;
    }

    public Timestamp getShipped_date() {
        return shipped_date;
    }

    public void setShipped_date(Timestamp shipped_date) {
        this.shipped_date = shipped_date;
    }

    public Short getShipper_id() {
        return shipper_id;
    }

    public void setShipper_id(Short shipper_id) {
        this.shipper_id = shipper_id;
    }

    public Short getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(Short payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public Short getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Short status_id) {
        this.status_id = status_id;
    }
}
