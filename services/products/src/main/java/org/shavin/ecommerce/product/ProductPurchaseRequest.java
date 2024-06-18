package org.shavin.ecommerce.product;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "Product is required")
        Integer productId,
        @NotNull(message = "Product quantity is required")
        double quantity
) {
}
