package com.market.shop.db.payment;

import com.market.shop.db.PaymentStatus;
import com.market.shop.exeption.PaymentNotFoundException;

public interface PaymentService {
    void changePaymentStatus(Long id, PaymentStatus paymentStatus) throws PaymentNotFoundException;
}