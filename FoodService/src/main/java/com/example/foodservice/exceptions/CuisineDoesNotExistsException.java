package com.example.foodservice.exceptions;

public class CuisineDoesNotExistsException extends RuntimeException {

    public CuisineDoesNotExistsException(String s) {
      super(s);
    }
}
