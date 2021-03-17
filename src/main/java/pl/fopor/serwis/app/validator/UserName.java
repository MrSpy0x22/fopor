package pl.fopor.serwis.app.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserNameValidator.class)
@Target(value = { ElementType.METHOD , ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserName {
    String message() default "Invalid username";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
