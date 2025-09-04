package com.example.orderservice.infrastructure.adapter.out.messaging;

import com.example.orderservice.application.port.out.OrderEventPublisherPort;
import com.example.orderservice.domain.model.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisherAdapter implements OrderEventPublisherPort {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    public OrderEventPublisherAdapter(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishOrderCreatedEvent(Order order) {
        kafkaTemplate.send("order-created", order);
    }
}
