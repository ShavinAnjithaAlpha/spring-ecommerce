package org.shavin.ecommerce.order;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.shavin.ecommerce.customer.CustomerClient;
import org.shavin.ecommerce.exception.BusinessException;
import org.shavin.ecommerce.kafka.OrderConfirmation;
import org.shavin.ecommerce.kafka.OrderProducer;
import org.shavin.ecommerce.orderline.OrderLineRequest;
import org.shavin.ecommerce.orderline.OrderLineService;
import org.shavin.ecommerce.product.ProductClient;
import org.shavin.ecommerce.product.PurchaseRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest request) {
        // check the customer
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID: " + request.customerId()));

        // purchase the product
        var purchaseProducts = this.productClient.purchaseProducts(request.products());

        // persist the order
        var order = repository.save(mapper.toOrder(request));

        // persis the order lines
        for (PurchaseRequest purchaseRequest: request.products()) {
                orderLineService.saveOrderLine(
                        new OrderLineRequest(
                                null,
                                order.getId(),
                                purchaseRequest.productId(),
                                purchaseRequest.quantity()
                        )
                );
        }

        // start payment process

        // send the order conformation to our notification service (to kafka broker)
        orderProducer.sendOrderConfirmation(new OrderConfirmation(
                request.reference(),
                request.amount(),
                request.paymentMethod(),
                customer,
                purchaseProducts
        ));

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());

    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId).map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("Order Not Found: no order with order id " + orderId));
    }
}
