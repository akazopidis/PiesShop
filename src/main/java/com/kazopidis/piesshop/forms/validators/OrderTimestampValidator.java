package com.kazopidis.piesshop.forms.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class OrderTimestampValidator implements ConstraintValidator<OrderTimestampConstraint, LocalDateTime> {
    @Override
    public void initialize(OrderTimestampConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDateTime timestamp, ConstraintValidatorContext constraintValidatorContext) {

        // for now true
        return true;

        // this code accepts timestamps 18.00 ... 22.00
        /*
            if (timestamp.getHour()<18 || timestamp.getHour()>=23)
                return false;
            else if (timestamp.getMinute()>0 || timestamp.getSecond()>0 || timestamp.getNano()>0)
                return false;
            return true;
        */

    }
}
