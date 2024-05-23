package com.kazopidis.piesshop.forms.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OrderTimestampValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderTimestampConstraint {
    String message() default "Wrong Order Time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
