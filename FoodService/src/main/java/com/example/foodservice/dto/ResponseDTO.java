package com.example.foodservice.dto;

import com.example.foodservice.model.Cuisine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO
{
    private int restId;

    private List<Cuisine> cuisine = new ArrayList<>();
}
