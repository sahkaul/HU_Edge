package com.example.user.mapping;

import com.example.user.dto.MyUserDTO;
import com.example.user.dto.ResponseDTO;
import com.example.user.model.MyUser;
import org.springframework.stereotype.Component;

@Component
public class MappingLayer
{
    public MyUserDTO mapUserToDTO(MyUser user)
    {
        MyUserDTO userDTO = new MyUserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public MyUser mapDTOToUser(MyUserDTO userDTO)
    {
        MyUser user = new MyUser();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;

    }

    public ResponseDTO mapUserToResponseDTO(MyUser user)
    {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setUsername(user.getUsername());
        responseDTO.setEmail(user.getEmail());
        return responseDTO;
    }


}
