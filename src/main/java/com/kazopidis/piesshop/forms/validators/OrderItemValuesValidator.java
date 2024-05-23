package com.kazopidis.piesshop.forms.validators;

import com.kazopidis.piesshop.models.model.OrderItem;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class OrderItemValuesValidator implements ConstraintValidator<OrderItemValuesConstraint, List<OrderItem>> {
    @Override
    public void initialize(OrderItemValuesConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<OrderItem> orderItems, ConstraintValidatorContext constraintValidatorContext) {
        for (OrderItem item: orderItems) {
            if (item.getQuantity() < 0 || item.getQuantity() > 100) {
                return false;
            }
        }
        return true;
    }
}
