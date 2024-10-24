package com.example.user.validation;

import com.example.user.model.MyUser;
import com.example.user.predefinedRoles.Roles;
import org.springframework.stereotype.Component;

@Component
public class ValidationLayer
{
    /**
     * this method is used for validating authority of a new user under registration
     * @param user
     * @return boolean
     */
    public String validateRegistration(MyUser user) {

        String validationErrors="";

        //Validating Email
        if (!user.getEmail().contains("@")) {
            validationErrors = validationErrors + "Invalid email ID - '@' is missing; ";
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
