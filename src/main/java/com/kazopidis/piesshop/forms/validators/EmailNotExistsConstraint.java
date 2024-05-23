package com.kazopidis.piesshop.forms.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailNotExistsValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailNotExistsConstraint  {
    String message() default "The email already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
