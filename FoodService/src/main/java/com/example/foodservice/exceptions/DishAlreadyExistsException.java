package com.example.foodservice.exceptions;

public class DishAlreadyExistsException extends RuntimeException
{
    public DishAlreadyExistsException(String message)
    {
        super(message);
    }

}
