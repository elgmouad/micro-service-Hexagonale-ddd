package com.example.paymentservice.application.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProcessPaymentUseCase {
    void processPayment(ProcessPaymentCommand command);

    class ProcessPaymentCommand {
        private final UUID orderId;
        private final BigDecimal amount;

        public ProcessPaymentCommand(UUID orderId, BigDecimal amount) {
            this.orderId = orderId;
            this.amount = amount;
        }

        public UUID getOrderId() {
            return orderId;
        }

        public BigDecimal getAmount() {
            return amount;
        }
    }
}
