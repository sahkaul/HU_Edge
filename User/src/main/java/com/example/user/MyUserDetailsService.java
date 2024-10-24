package com.example.user;

import com.example.user.model.MyUser;
import com.example.user.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MyUser user = myUserRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
         List<GrantedAuthority> list = new ArrayList<>();

         list.add(new SimpleGrantedAuthority(user.getAuthority()));

        return new User(user.getUsername(), user.getPassword(), list);

    }
}
