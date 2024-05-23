package com.kazopidis.piesshop.forms.validators;

import com.kazopidis.piesshop.models.dao.UserDAO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailExistsValidator implements ConstraintValidator<EmailExistsConstraint, String> {
    @Override
    public void initialize(EmailExistsConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return UserDAO.getUserByEmail(email) != null;
    }
}
