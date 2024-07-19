package com.example.shoppingmanager.exception;

public class BasketException extends Exception {
    public BasketException(String message, Object ... args) {
        super(String.format(message, args));
    }
}
