package com.example.orderservice.infrastructure.adapter.in.web;

import com.example.orderservice.application.port.in.CreateOrderUseCase;
import com.example.orderservice.application.port.in.GetOrderUseCase;
import com.example.orderservice.domain.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateOrderUseCase createOrderUseCase;

    @MockBean
    private GetOrderUseCase getOrderUseCase;

    @Test
    void testCreateOrder() throws Exception {
        CreateOrderUseCase.CreateOrderCommand command = new CreateOrderUseCase.CreateOrderCommand(UUID.randomUUID(), Collections.emptyList());
        Order order = new Order(command.getCustomerId(), Collections.emptyList());
        when(createOrderUseCase.createOrder(any(CreateOrderUseCase.CreateOrderCommand.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId().toString()));
    }

    @Test
    void testGetOrder() throws Exception {
        UUID orderId = UUID.randomUUID();
        Order order = new Order(UUID.randomUUID(), Collections.emptyList());
        when(getOrderUseCase.getOrder(orderId)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/orders/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId().toString()));
    }

    @Test
    void testGetOrderNotFound() throws Exception {
        UUID orderId = UUID.randomUUID();
        when(getOrderUseCase.getOrder(orderId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/orders/{orderId}", orderId))
                .andExpect(status().isNotFound());
    }
}
