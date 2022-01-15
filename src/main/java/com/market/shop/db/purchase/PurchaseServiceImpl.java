package com.market.shop.db.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseServiceImpl (PurchaseRepository purchaseRepository) {this.purchaseRepository=purchaseRepository;}

}
