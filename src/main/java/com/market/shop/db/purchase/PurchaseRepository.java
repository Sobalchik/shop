package com.market.shop.db.purchase;

import com.market.shop.rest.dto.cake.Cake;
import com.market.shop.rest.dto.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
}