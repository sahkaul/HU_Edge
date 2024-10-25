package com.example.user.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUserDTO
{

    private Long id;

    private String username;

    private String password;

    private String email;

    private String authority;

}
