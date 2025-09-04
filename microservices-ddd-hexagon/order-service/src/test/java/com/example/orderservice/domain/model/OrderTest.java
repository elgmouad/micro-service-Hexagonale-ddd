package com.example.orderservice.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testPayOrder() {
        Order order = new Order(UUID.randomUUID(), Collections.emptyList());
        assertEquals(OrderStatus.CREATED, order.getStatus());
        order.pay();
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    void testCancelOrder() {
        Order order = new Order(UUID.randomUUID(), Collections.emptyList());
        assertEquals(OrderStatus.CREATED, order.getStatus());
        order.cancel();
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void testPayCancelledOrderThrowsException() {
        Order order = new Order(UUID.randomUUID(), Collections.emptyList());
        order.cancel();
        assertThrows(IllegalStateException.class, order::pay);
    }

    @Test
    void testCancelPaidOrderThrowsException() {
        Order order = new Order(UUID.randomUUID(), Collections.emptyList());
        order.pay();
        assertThrows(IllegalStateException.class, order::cancel);
    }

    @Test
    void testTotalPriceCalculation() {
        OrderItem item1 = new OrderItem(UUID.randomUUID(), 2, new BigDecimal("10.00"));
        OrderItem item2 = new OrderItem(UUID.randomUUID(), 1, new BigDecimal("5.50"));
        Order order = new Order(UUID.randomUUID(), java.util.Arrays.asList(item1, item2));
        assertEquals(new BigDecimal("25.50"), order.getTotalPrice());
    }
}
