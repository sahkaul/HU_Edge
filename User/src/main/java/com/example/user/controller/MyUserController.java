package com.example.user.controller;


import com.example.user.Exception.InvalidUserRegistrationException;
import com.example.user.constants.SecurityConstants;
import com.example.user.dto.ResponseDTO;
import com.example.user.mapping.MappingLayer;
import com.example.user.validation.ValidationLayer;
import com.example.user.model.MyUser;
import com.example.user.repository.MyUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/user")
public class MyUserController {

    @Autowired
    MappingLayer mappingLayer;

    @Autowired
    private MyUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidationLayer validationLayer;

    @Autowired
    private HttpServletRequest request;

    /**
     * This method is responsible for the registration of a new user
     * @param user
     * @return ResponseEntity
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MyUser user) {

            String validationErrors = validationLayer.validateRegistration(user);

            if(!validationErrors.isEmpty())
            {
              throw new InvalidUserRegistrationException(validationErrors);
            }

            String hashPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPassword);

            MyUser savedUser = userRepository.save(user);

            if (savedUser.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Registration Failed");
            }
    }

    /**
     * This method is responsible for getting the details of a registered User
     * @param userId
     * @return ResponseEntity
     */
    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam("userId") Long userId)
    {
       Optional<MyUser> optionalUser= userRepository.findById(userId);

       if(optionalUser.isPresent())
       {
           return ResponseEntity.status(HttpStatus.OK).body(optionalUser.get());
       }
       else {
           throw new UsernameNotFoundException("User not found");
       }
    }

    /**
     * This method/api validates user credentials and returns a JWT token as a response header
     * @param authentication
     * @return
     */
    @PostMapping("/login")
    public MyUser getUserDetailsAfterLogin(Authentication authentication) {

        Optional<?> optionalUser = userRepository.findByUsername(authentication.getName());

        if(optionalUser.isPresent()) {
            return (MyUser) optionalUser.get();
        }
        else {
            return null;
        }

    }

    @PutMapping("/resetPassword")
    public String resetPassword(@RequestParam("newPassword") String newPassword)
    {
        String jwt = request.getHeader(SecurityConstants.JWT_HEADER); //name of response header via which we had initially sent the token
        jwt = jwt.substring(7);

        SecretKey key = Keys.hmacShaKeyFor(
                SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));


        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt) //This will throw exceptions if hash value isn't matching,token is expired etc
                .getPayload();

        String username = String.valueOf(claims.get("username"));

        MyUser savedUser = userRepository.findByUsername(username).get();

        savedUser.setPassword(newPassword);
        userRepository.save(savedUser);

        return "Password reset successfully";

    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers()
    {
        Iterable<MyUser> myUserIterable= userRepository.findAll();

        List<ResponseDTO> responseDTOList = new ArrayList<>();

        for(MyUser user: myUserIterable)
        {
            responseDTOList.add(mappingLayer.mapUserToResponseDTO(user));
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
    }






}


