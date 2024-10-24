package com.example.foodservice.exceptions;

public class InvalidCuisineException extends RuntimeException
{
    public InvalidCuisineException(String message)
    {
        super(message);
    }
}

