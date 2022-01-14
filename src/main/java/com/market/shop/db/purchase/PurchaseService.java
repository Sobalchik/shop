package com.market.shop.db.purchase;

import com.market.shop.db.goods.CakeEntity;
import com.market.shop.db.orders.OrderEntity;

public interface PurchaseService {
    void addPurchase(OrderEntity orderEntity, CakeEntity cakeEntity, Integer count);
}