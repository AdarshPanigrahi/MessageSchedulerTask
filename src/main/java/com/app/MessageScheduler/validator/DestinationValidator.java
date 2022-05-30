package com.app.MessageScheduler.validator;

import com.google.i18n.phonenumbers.PhoneNumberUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DestinationValidator implements ConstraintValidator<validPhone, String> {


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            PhoneNumberUtil phoneNumberUtil=PhoneNumberUtil.getInstance();
            return phoneNumberUtil.isValidNumber(phoneNumberUtil.parse(s,"IN"));
        }catch (Exception e){
            return false;
        }
    }
}
