package com.example.orderservice.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItem {
    private final UUID id;
    private final UUID productId;
    private final int quantity;
    private final BigDecimal price;
    private final BigDecimal subTotal;

    public OrderItem(UUID productId, int quantity, BigDecimal price) {
        this.id = UUID.randomUUID();
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = price.multiply(new BigDecimal(quantity));
    }

    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }
}
