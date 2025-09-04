package com.example.orderservice.infrastructure.adapter.out.persistence;

import com.example.orderservice.application.port.out.OrderRepositoryPort;
import com.example.orderservice.domain.model.Order;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final SpringDataOrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderRepositoryAdapter(SpringDataOrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity orderJpaEntity = orderMapper.toJpaEntity(order);
        OrderJpaEntity savedOrder = orderRepository.save(orderJpaEntity);
        return orderMapper.toDomainEntity(savedOrder);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id).map(orderMapper::toDomainEntity);
    }
}
