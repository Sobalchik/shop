package com.market.shop.db.orders;

import com.market.shop.db.Delivery;
import com.market.shop.db.OrderStatus;
import com.market.shop.db.payment.PaymentEntity;
import com.market.shop.db.purchase.PurchaseEntity;
import com.market.shop.db.users.UserEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "ORDERINFO")
public class OrderEntity {

    @Setter(AccessLevel.NONE)
    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Setter(AccessLevel.PROTECTED)
    @ToString.Exclude
    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private PaymentEntity payment;

    @Setter(AccessLevel.PROTECTED)
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private UserEntity user;

    @Setter(AccessLevel.PROTECTED)
    @ToString.Exclude
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PurchaseEntity> purchases;

    @Setter(AccessLevel.PROTECTED)
    private Delivery delivery;

    @Setter(AccessLevel.PROTECTED)
    private String deliveryAddress;

    @Setter(AccessLevel.PROTECTED)
    private String deliveryTime;

    @Setter(AccessLevel.PROTECTED)
    private OrderStatus orderStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderEntity that = (OrderEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}