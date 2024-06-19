package org.shavin.ecommerce.orderline;

import jakarta.persistence.*;
import lombok.*;
import org.shavin.ecommerce.order.Order;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table
public class  OrderLine {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "order_id"
    )
    private Order order;
    private Integer productId;
    private double quantity;
}
