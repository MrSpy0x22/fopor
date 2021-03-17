package pl.fopor.serwis.app.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameValidator implements ConstraintValidator<UserName , String> {
    @Override
    public void initialize(UserName constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && s.toLowerCase().matches("^[a-z0-9-_.+]$");
    }
}
