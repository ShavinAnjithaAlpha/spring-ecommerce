package org.shavin.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer firstname is required")
        String firstName,
        @NotNull(message = "Customer lastname is required")
        String lastName,
        @NotNull(message = "Customer email is required")
                @Email(message = "Customer email is a not a valid email address")
        String email,
        Address address
) {
}
