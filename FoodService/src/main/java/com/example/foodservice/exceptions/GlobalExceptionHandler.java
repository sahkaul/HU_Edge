package com.example.foodservice.exceptions;

import com.example.foodservice.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRestaurantException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidRestaurantException(Exception exception)
    {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCuisineException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCuisineException(Exception exception)
    {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CuisineAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCuisineAlreadyExistsException(Exception exception)
    {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CuisineDoesNotExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCuisineDoesNotExistsException(Exception exception)
    {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDishException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidDishException(Exception exception)
    {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DishAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleDishAlreadyExistsException(Exception exception)
    {
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

}
