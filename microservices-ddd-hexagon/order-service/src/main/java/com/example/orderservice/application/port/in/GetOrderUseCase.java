package com.example.orderservice.application.port.in;

import com.example.orderservice.domain.model.Order;
import java.util.Optional;
import java.util.UUID;

public interface GetOrderUseCase {
    Optional<Order> getOrder(UUID orderId);
}
