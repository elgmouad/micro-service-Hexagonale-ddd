package com.example.paymentservice.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void testCompletePayment() {
        Payment payment = new Payment(UUID.randomUUID(), new BigDecimal("100.00"));
        assertEquals(PaymentStatus.PENDING, payment.getStatus());
        payment.complete();
        assertEquals(PaymentStatus.COMPLETED, payment.getStatus());
    }

    @Test
    void testFailPayment() {
        Payment payment = new Payment(UUID.randomUUID(), new BigDecimal("100.00"));
        assertEquals(PaymentStatus.PENDING, payment.getStatus());
        payment.fail();
        assertEquals(PaymentStatus.FAILED, payment.getStatus());
    }

    @Test
    void testCompleteFailedPaymentThrowsException() {
        Payment payment = new Payment(UUID.randomUUID(), new BigDecimal("100.00"));
        payment.fail();
        assertThrows(IllegalStateException.class, payment::complete);
    }

    @Test
    void testFailCompletedPaymentThrowsException() {
        Payment payment = new Payment(UUID.randomUUID(), new BigDecimal("100.00"));
        payment.complete();
        assertThrows(IllegalStateException.class, payment::fail);
    }
}
