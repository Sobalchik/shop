package com.market.shop.exeption;

public class CakeNotFoundException extends RuntimeException {
    public CakeNotFoundException(String message) {
        super(message);
    }
}
