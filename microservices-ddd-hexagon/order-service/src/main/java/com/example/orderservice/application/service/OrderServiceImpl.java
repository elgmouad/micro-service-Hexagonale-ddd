package com.example.orderservice.application.service;

import com.example.orderservice.application.port.in.CreateOrderUseCase;
import com.example.orderservice.application.port.in.GetOrderUseCase;
import com.example.orderservice.application.port.out.OrderEventPublisherPort;
import com.example.orderservice.application.port.out.OrderRepositoryPort;
import com.example.orderservice.domain.model.Order;
import com.example.orderservice.domain.model.OrderItem;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements CreateOrderUseCase, GetOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderEventPublisherPort orderEventPublisherPort;

    public OrderServiceImpl(OrderRepositoryPort orderRepositoryPort, OrderEventPublisherPort orderEventPublisherPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.orderEventPublisherPort = orderEventPublisherPort;
    }

    @Override
    public Order createOrder(CreateOrderCommand command) {
        List<OrderItem> items = command.getItems().stream()
                .map(item -> new OrderItem(item.getProductId(), item.getQuantity(), item.getPrice()))
                .collect(Collectors.toList());
        Order order = new Order(command.getCustomerId(), items);
        Order savedOrder = orderRepositoryPort.save(order);
        orderEventPublisherPort.publishOrderCreatedEvent(savedOrder);
        return savedOrder;
    }

    @Override
    public Optional<Order> getOrder(UUID orderId) {
        return orderRepositoryPort.findById(orderId);
    }
}
