package com.example.paymentservice.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class PaymentJpaEntity {
    @Id
    private UUID id;
    private UUID orderId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatusJpaEnum status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatusJpaEnum getStatus() {
        return status;
    }

    public void setStatus(PaymentStatusJpaEnum status) {
        this.status = status;
    }
}
