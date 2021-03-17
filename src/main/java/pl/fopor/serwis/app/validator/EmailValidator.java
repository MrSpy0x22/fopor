package pl.fopor.serwis.app.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email , String> {

    @Override
    public void initialize(Email constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return false;
        else return s.length() < 64 || s.toLowerCase().matches("^[a-z0-9-+_~.]+@[a-z0-9-+_~]+\\.+[a-z0-9-+_~.]+$");
    }

}
