package com.example.paymentservice.infrastructure.adapter.out.persistence;

import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.model.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentJpaEntity toJpaEntity(Payment payment) {
        PaymentJpaEntity jpaEntity = new PaymentJpaEntity();
        jpaEntity.setId(payment.getId());
        jpaEntity.setOrderId(payment.getOrderId());
        jpaEntity.setAmount(payment.getAmount());
        jpaEntity.setStatus(PaymentStatusJpaEnum.valueOf(payment.getStatus().name()));
        return jpaEntity;
    }

    public Payment toDomainEntity(PaymentJpaEntity jpaEntity) {
        Payment payment = new Payment(
                jpaEntity.getOrderId(),
                jpaEntity.getAmount()
        );
        // Same hack as in OrderMapper
        setPrivateField(payment, "id", jpaEntity.getId());
        setPrivateField(payment, "status", PaymentStatus.valueOf(jpaEntity.getStatus().name()));
        return payment;
    }

    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
