package com.example.foodservice.exceptions;

public class InvalidDishException extends RuntimeException {
    public InvalidDishException(String s) {
        super(s);
    }
}
