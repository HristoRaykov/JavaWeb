package thymeleafexercise.residentevil.annotation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreatorValidator implements ConstraintValidator<Creator, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.equals("Corp") || value.equals("corp");
    }

}
