package com.example.user.filter;

import com.example.user.constants.SecurityConstants;
import com.example.user.model.MyUser;
import com.example.user.repository.MyUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    @Autowired
    MyUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //jwt generation occurs after authentication is successful
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // this will return the currently AUTHENTICATED user information

        if (null != authentication)
        {
            //generating secret key
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8)); //Yeh JWT key will only be in possesion of backend

            //creating jwt token
            String jwt = Jwts.builder().issuer("Sahil Kaul").subject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 30000000))
                    .signWith(key).compact();  //signature

            response.setHeader(SecurityConstants.JWT_HEADER, jwt);  //sending jwt token as response header
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/user/login"); // this filter will not be executed for other paths. It will only be executed for /user i.e login
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

}