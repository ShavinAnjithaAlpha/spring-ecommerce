package org.shavin.ecommerce.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentMapper {


    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .id(request.id())
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .orderId(request.orderId())
                .build();
    }
}
