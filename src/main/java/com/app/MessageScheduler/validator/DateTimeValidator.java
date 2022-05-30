package com.app.MessageScheduler.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class DateTimeValidator implements ConstraintValidator<validDateTime,String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            LocalDateTime.parse(s);
        }catch (Exception e){
            return false;
        }

        return true;
    }
}
