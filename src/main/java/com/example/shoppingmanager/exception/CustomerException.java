package com.example.shoppingmanager.exception;

public class CustomerException extends Exception {
    public CustomerException(String message, Object ... args) {
        super(String.format(message, args));
    }
}
