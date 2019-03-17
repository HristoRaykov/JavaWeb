package thymeleafexercise.residentevil.annotation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleasedOnValidator implements ConstraintValidator<RelealedOn, LocalDate> {


    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value==null){
            return false;
        }
        LocalDate today = LocalDate.now();
        return value.isBefore(today);
    }

}
