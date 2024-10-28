package com.example.user.validation;

import com.example.user.Exception.InvalidUserRegistrationException;
import com.example.user.model.MyUser;
import com.example.user.predefinedRoles.Roles;
import com.example.user.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidationLayer
{

    @Autowired
    MyUserRepository myUserRepository;
    /**
     * this method is used for validating authority of a new user under registration
     * @param user
     * @return boolean
     */
    public String validateRegistration(MyUser user) {

        String validationErrors="";

        Optional<MyUser> savedUser = myUserRepository.findByEmail(user.getEmail());

        if(savedUser.isPresent()) {
            throw new InvalidUserRegistrationException("A user with this email already exists");
        }

        //Validating Email
        if (!user.getEmail().contains("@") ) {
            validationErrors = validationErrors + " '@' is missing in email; ";
        }

        if (!user.getEmail().contains(".com") ) {
            validationErrors = validationErrors + "domain name is missing in email; ";
        }

        //Validating username
        if (user.getUsername().length() < 5)
        {
            validationErrors= validationErrors + "UserName must be at least 5 characters; ";
        }

        //Validating Authority
        boolean flag=false;
        for (Roles role : Roles.values())
        {
          if(role.name().equals(user.getAuthority())) {
              flag= true;
          }
        }

        if(!flag)
        {
            validationErrors = validationErrors + "Specified authority does not exist";
        }

        return validationErrors;
    }
}
