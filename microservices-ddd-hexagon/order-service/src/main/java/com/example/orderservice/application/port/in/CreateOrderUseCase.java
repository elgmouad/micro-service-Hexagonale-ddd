package com.example.orderservice.application.port.in;

import com.example.orderservice.domain.model.Order;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

public interface CreateOrderUseCase {
    Order createOrder(CreateOrderCommand command);

    class CreateOrderCommand {
        private final UUID customerId;
        private final List<OrderItemCommand> items;

        public CreateOrderCommand(UUID customerId, List<OrderItemCommand> items) {
            this.customerId = customerId;
            this.items = items;
        }

        public UUID getCustomerId() {
            return customerId;
        }

        public List<OrderItemCommand> getItems() {
            return items;
        }
    }

    class OrderItemCommand {
        private final UUID productId;
        private final int quantity;
        private final BigDecimal price;

        public OrderItemCommand(UUID productId, int quantity, BigDecimal price) {
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
        }

        public UUID getProductId() {
            return productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }
}
