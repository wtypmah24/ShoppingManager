package com.example.shoppingmanager.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomerException.class, ProductException.class, BasketException.class})
    protected ResponseEntity<Object> handleIllegalState(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex, "", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
