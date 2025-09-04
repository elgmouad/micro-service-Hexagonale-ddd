package com.example.paymentservice.infrastructure.adapter.in.messaging.event;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderCreatedEvent {
    private UUID id;
    private BigDecimal totalPrice;
    // other fields from the Order object that might be relevant

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
