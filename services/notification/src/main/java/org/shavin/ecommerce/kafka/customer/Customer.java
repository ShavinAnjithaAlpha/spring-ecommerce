package org.shavin.ecommerce.kafka.customer;

public record Customer(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
