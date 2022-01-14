package com.market.shop.db.payment;


import com.market.shop.db.PaymentStatus;

import com.market.shop.db.orders.OrderEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "PAYMENTINFO")
public class PaymentEntity {
    @Setter(AccessLevel.NONE)
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Setter(AccessLevel.PUBLIC)
    private Double amount;

    @Setter(AccessLevel.PUBLIC)
    private PaymentStatus status;

    @Setter(AccessLevel.PUBLIC)
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private OrderEntity order;
}
