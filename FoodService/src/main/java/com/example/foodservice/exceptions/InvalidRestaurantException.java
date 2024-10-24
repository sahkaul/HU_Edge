package com.example.foodservice.exceptions;

public class InvalidRestaurantException extends RuntimeException
{
    public InvalidRestaurantException(String message)
    {
        super(message);
    }
}
