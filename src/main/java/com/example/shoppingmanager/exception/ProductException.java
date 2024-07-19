package com.example.shoppingmanager.exception;

public class ProductException extends Exception {
    public ProductException(String message, Object ... args) {
        super(String.format(message, args));
    }
}
