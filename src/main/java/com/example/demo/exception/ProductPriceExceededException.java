package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductPriceExceededException extends RuntimeException {
    public ProductPriceExceededException(String message) {
        super(message);
    }
}