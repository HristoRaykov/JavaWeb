package thymeleafexercise.residentevil.annotation.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = ReleasedOnValidator.class)
public @interface RelealedOn {

    String message() default "Should be before the \"today\" date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
