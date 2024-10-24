package com.example.foodservice.exceptions;

public class CuisineAlreadyExistsException extends RuntimeException {

    public CuisineAlreadyExistsException(String s) {
      super(s);
    }
}
