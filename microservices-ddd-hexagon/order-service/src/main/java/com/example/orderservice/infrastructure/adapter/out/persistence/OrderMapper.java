package com.example.orderservice.infrastructure.adapter.out.persistence;

import com.example.orderservice.domain.model.Order;
import com.example.orderservice.domain.model.OrderItem;
import com.example.orderservice.domain.model.OrderStatus;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderJpaEntity toJpaEntity(Order order) {
        OrderJpaEntity jpaEntity = new OrderJpaEntity();
        jpaEntity.setId(order.getId());
        jpaEntity.setCustomerId(order.getCustomerId());
        jpaEntity.setTotalPrice(order.getTotalPrice());
        jpaEntity.setStatus(OrderStatusJpaEnum.valueOf(order.getStatus().name()));
        jpaEntity.setItems(order.getItems().stream()
                .map(item -> toJpaEntity(item, jpaEntity))
                .collect(Collectors.toList()));
        return jpaEntity;
    }

    public Order toDomainEntity(OrderJpaEntity jpaEntity) {
        Order order = new Order(
                jpaEntity.getCustomerId(),
                jpaEntity.getItems().stream()
                        .map(this::toDomainEntity)
                        .collect(Collectors.toList())
        );
        // The domain entity constructor sets the ID, total price and status.
        // We need to override them with the values from the database.
        // This is a bit of a hack. A better way would be to have a private constructor
        // in the domain entity that takes all the fields as arguments.
        // For now, this will do.
        setPrivateField(order, "id", jpaEntity.getId());
        setPrivateField(order, "status", OrderStatus.valueOf(jpaEntity.getStatus().name()));
        return order;
    }

    private OrderItemJpaEntity toJpaEntity(OrderItem orderItem, OrderJpaEntity order) {
        OrderItemJpaEntity jpaEntity = new OrderItemJpaEntity();
        jpaEntity.setId(orderItem.getId());
        jpaEntity.setProductId(orderItem.getProductId());
        jpaEntity.setPrice(orderItem.getPrice());
        jpaEntity.setQuantity(orderItem.getQuantity());
        jpaEntity.setOrder(order);
        return jpaEntity;
    }

    private OrderItem toDomainEntity(OrderItemJpaEntity jpaEntity) {
        OrderItem item = new OrderItem(
                jpaEntity.getProductId(),
                jpaEntity.getQuantity(),
                jpaEntity.getPrice()
        );
        // Same hack as above to set the ID.
        setPrivateField(item, "id", jpaEntity.getId());
        return item;
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
