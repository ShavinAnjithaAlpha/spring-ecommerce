package org.shavin.ecommerce.kafka.order;

import org.shavin.ecommerce.kafka.customer.Customer;
import org.shavin.ecommerce.kafka.payment.PaymentMethod;
import org.shavin.ecommerce.kafka.product.Product;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Customer customer,
        List<Product> products
) {
}
