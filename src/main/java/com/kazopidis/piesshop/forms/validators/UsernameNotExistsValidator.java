package com.kazopidis.piesshop.forms.validators;

import com.kazopidis.piesshop.models.dao.UserDAO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameNotExistsValidator implements ConstraintValidator<UsernameNotExistsConstraint, String> {

    @Override
    public void initialize(UsernameNotExistsConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /*
    If the result is null, it means that no user with that username exists, and the method returns true, indicating that the username is valid.
    If a user with the given username is found, the method returns false, indicating that the username is not valid.
     */

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return UserDAO.getUserByUsername(username) == null;
    }
}
