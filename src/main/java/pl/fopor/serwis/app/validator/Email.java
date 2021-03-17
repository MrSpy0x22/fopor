package pl.fopor.serwis.app.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target(value = { ElementType.METHOD , ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
    String message() default "Invalid email";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
