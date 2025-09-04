package com.example.paymentservice.infrastructure.adapter.in.web;

import com.example.paymentservice.application.port.in.GetPaymentUseCase;
import com.example.paymentservice.domain.model.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final GetPaymentUseCase getPaymentUseCase;

    public PaymentController(GetPaymentUseCase getPaymentUseCase) {
        this.getPaymentUseCase = getPaymentUseCase;
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable UUID orderId) {
        return getPaymentUseCase.getPaymentByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
