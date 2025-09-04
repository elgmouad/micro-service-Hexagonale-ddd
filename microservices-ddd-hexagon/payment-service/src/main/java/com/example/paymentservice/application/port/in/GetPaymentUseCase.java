package com.example.paymentservice.application.port.in;

import com.example.paymentservice.domain.model.Payment;
import java.util.Optional;
import java.util.UUID;

public interface GetPaymentUseCase {
    Optional<Payment> getPaymentByOrderId(UUID orderId);
}
