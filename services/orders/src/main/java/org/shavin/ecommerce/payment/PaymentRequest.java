package org.shavin.ecommerce.payment;

import org.shavin.ecommerce.customer.CustomerResponse;
import org.shavin.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
