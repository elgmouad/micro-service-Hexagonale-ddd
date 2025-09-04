package com.example.paymentservice.application.service;

import com.example.paymentservice.application.port.in.GetPaymentUseCase;
import com.example.paymentservice.application.port.in.ProcessPaymentUseCase;
import com.example.paymentservice.application.port.out.PaymentRepositoryPort;
import com.example.paymentservice.domain.model.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PaymentServiceImpl implements ProcessPaymentUseCase, GetPaymentUseCase {

    private final PaymentRepositoryPort paymentRepositoryPort;

    public PaymentServiceImpl(PaymentRepositoryPort paymentRepositoryPort) {
        this.paymentRepositoryPort = paymentRepositoryPort;
    }

    @Override
    public void processPayment(ProcessPaymentCommand command) {
        Payment payment = new Payment(command.getOrderId(), command.getAmount());
        // In a real application, we would call an external payment gateway here.
        // For this example, we will just complete the payment.
        payment.complete();
        paymentRepositoryPort.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentByOrderId(UUID orderId) {
        return paymentRepositoryPort.findByOrderId(orderId);
    }
}
