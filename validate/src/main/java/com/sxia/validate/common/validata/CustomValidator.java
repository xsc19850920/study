package com.sxia.validate.common.validata;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CustomValidator implements ConstraintValidator<CustomValid,String> {
    private List<String> list ;
    @Override
    public void initialize(CustomValid constraintAnnotation) {
        list = Arrays.asList(constraintAnnotation.values());
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!list.contains(value)){
           return false;
        }
        return true;
    }
}
