package com.market.shop.db.purchase;

import com.market.shop.db.goods.CakeEntity;
import com.market.shop.db.orders.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseServiceImpl (PurchaseRepository purchaseRepository) {this.purchaseRepository=purchaseRepository;}

}
