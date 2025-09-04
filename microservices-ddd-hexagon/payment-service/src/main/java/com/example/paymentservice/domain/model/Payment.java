package com.example.paymentservice.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Payment {
    private final UUID id;
    private final UUID orderId;
    private final BigDecimal amount;
    private PaymentStatus status;

    public Payment(UUID orderId, BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    public void complete() {
        if (this.status == PaymentStatus.PENDING) {
            this.status = PaymentStatus.COMPLETED;
        } else {
            throw new IllegalStateException("Payment is not in a state to be completed");
        }
    }

    public void fail() {
        if (this.status == PaymentStatus.PENDING) {
            this.status = PaymentStatus.FAILED;
        } else {
            throw new IllegalStateException("Payment is not in a state to be failed");
        }
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}
