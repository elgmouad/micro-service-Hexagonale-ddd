package com.example.paymentservice.infrastructure.adapter.out.persistence;

import com.example.paymentservice.application.port.out.PaymentRepositoryPort;
import com.example.paymentservice.domain.model.Payment;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
public class PaymentRepositoryAdapter implements PaymentRepositoryPort {

    private final SpringDataPaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentRepositoryAdapter(SpringDataPaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentJpaEntity paymentJpaEntity = paymentMapper.toJpaEntity(payment);
        PaymentJpaEntity savedPayment = paymentRepository.save(paymentJpaEntity);
        return paymentMapper.toDomainEntity(savedPayment);
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return paymentRepository.findByOrderId(orderId).map(paymentMapper::toDomainEntity);
    }
}
