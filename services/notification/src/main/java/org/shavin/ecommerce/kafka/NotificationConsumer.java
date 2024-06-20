package org.shavin.ecommerce.kafka;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shavin.ecommerce.email.EmailService;
import org.shavin.ecommerce.kafka.order.OrderConfirmation;
import org.shavin.ecommerce.kafka.payment.NotificationRepository;
import org.shavin.ecommerce.kafka.payment.PaymentConfirmation;
import org.shavin.ecommerce.notification.Notification;
import org.shavin.ecommerce.notification.NotificationType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;
     private final EmailService emailService;

    @Async
    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from payment topic Topic:: %s", paymentConfirmation));
        repository.save(Notification.builder()
                .notificationDate(LocalDateTime.now())
                .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                .build());

         // send email
        emailService.sendPaymentSuccessEmail(paymentConfirmation.customerEmail(),
                String.format("%s %s", paymentConfirmation.customerFirstName(), paymentConfirmation.customerLastName()),
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference());
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderSuccessNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(String.format("Consuming the message from order topic Topic:: %s", orderConfirmation));
        repository.save(Notification.builder()
                .notificationDate(LocalDateTime.now())
                .notificationType(NotificationType.ORDER_CONFIRMATION)
                .build());

        // send email
        emailService.sendOrderConfirmationEmail(orderConfirmation.customer().email(),
                String.format("%s %s", orderConfirmation.customer().firstName(), orderConfirmation.customer().lastName()),
                orderConfirmation.amount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
                );
    }

}
