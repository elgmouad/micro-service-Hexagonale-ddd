package com.example.orderservice.application.port.out;

import com.example.orderservice.domain.model.Order;

public interface OrderEventPublisherPort {
    void publishOrderCreatedEvent(Order order);
}
