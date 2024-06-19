package org.shavin.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.shavin.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .id(orderLineRequest.id())
                .quantity(orderLineRequest.quantity())
                .productId(orderLineRequest.productId())
                .order(
                        Order.builder()
                                .id(orderLineRequest.orderId())
                                .build()
                )
                .build();
    }
}
