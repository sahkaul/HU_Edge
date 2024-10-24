package com.example.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDto
{

    private HttpStatus errorCode;

    private  String errorMessage;

    private LocalDateTime errorTime;
}
