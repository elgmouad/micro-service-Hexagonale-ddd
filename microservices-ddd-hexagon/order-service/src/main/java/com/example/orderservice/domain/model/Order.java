package com.example.orderservice.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID id;
    private final UUID customerId;
    private final List<OrderItem> items;
    private final BigDecimal totalPrice;
    private OrderStatus status;

    public Order(UUID customerId, List<OrderItem> items) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.items = items;
        this.totalPrice = items.stream()
                .map(OrderItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.status = OrderStatus.CREATED;
    }

    public void pay() {
        if (this.status == OrderStatus.CREATED) {
            this.status = OrderStatus.PAID;
        } else {
            throw new IllegalStateException("Order is not in a state to be paid");
        }
    }

    public void cancel() {
        if (this.status == OrderStatus.CREATED) {
            this.status = OrderStatus.CANCELLED;
        } else {
            throw new IllegalStateException("Order is not in a state to be cancelled");
        }
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
