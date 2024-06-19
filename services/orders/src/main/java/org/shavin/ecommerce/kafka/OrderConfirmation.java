package org.shavin.ecommerce.kafka;

import org.shavin.ecommerce.customer.CustomerResponse;
import org.shavin.ecommerce.order.PaymentMethod;
import org.shavin.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse response,
        List<PurchaseResponse> purchaseProducts
) {
}
