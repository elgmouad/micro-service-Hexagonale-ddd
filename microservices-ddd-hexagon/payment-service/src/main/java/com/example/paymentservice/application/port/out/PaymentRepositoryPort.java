package com.example.paymentservice.application.port.out;

import com.example.paymentservice.domain.model.Payment;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepositoryPort {
    Payment save(Payment payment);
    Optional<Payment> findByOrderId(UUID orderId);
}
