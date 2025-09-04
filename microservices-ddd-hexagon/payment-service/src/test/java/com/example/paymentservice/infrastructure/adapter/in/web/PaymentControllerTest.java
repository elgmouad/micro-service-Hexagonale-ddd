package com.example.paymentservice.infrastructure.adapter.in.web;

import com.example.paymentservice.application.port.in.GetPaymentUseCase;
import com.example.paymentservice.domain.model.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetPaymentUseCase getPaymentUseCase;

    @Test
    void testGetPaymentByOrderId() throws Exception {
        UUID orderId = UUID.randomUUID();
        Payment payment = new Payment(orderId, new BigDecimal("100.00"));
        when(getPaymentUseCase.getPaymentByOrderId(orderId)).thenReturn(Optional.of(payment));

        mockMvc.perform(get("/payments/order/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(orderId.toString()));
    }

    @Test
    void testGetPaymentByOrderIdNotFound() throws Exception {
        UUID orderId = UUID.randomUUID();
        when(getPaymentUseCase.getPaymentByOrderId(orderId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/payments/order/{orderId}", orderId))
                .andExpect(status().isNotFound());
    }
}
