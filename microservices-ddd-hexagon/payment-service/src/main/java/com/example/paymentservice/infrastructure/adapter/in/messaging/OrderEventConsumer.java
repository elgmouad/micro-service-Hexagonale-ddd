package com.example.paymentservice.infrastructure.adapter.in.messaging;

import com.example.paymentservice.application.port.in.ProcessPaymentUseCase;
import com.example.paymentservice.infrastructure.adapter.in.messaging.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    private final ProcessPaymentUseCase processPaymentUseCase;

    public OrderEventConsumer(ProcessPaymentUseCase processPaymentUseCase) {
        this.processPaymentUseCase = processPaymentUseCase;
    }

    @KafkaListener(topics = "order-created", groupId = "payment-service")
    public void consume(OrderCreatedEvent event) {
        ProcessPaymentUseCase.ProcessPaymentCommand command = new ProcessPaymentUseCase.ProcessPaymentCommand(
                event.getId(),
                event.getTotalPrice()
        );
        processPaymentUseCase.processPayment(command);
    }
}
