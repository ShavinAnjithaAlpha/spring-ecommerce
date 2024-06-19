package org.shavin.ecommerce.order;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
