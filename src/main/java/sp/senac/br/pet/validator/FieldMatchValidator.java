package sp.senac.br.pet.validator;

import org.springframework.beans.BeanWrapperImpl;
import sp.senac.br.pet.constraint.FieldMatch;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;


    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        Object firstObj = new BeanWrapperImpl(value)
                .getPropertyValue(firstFieldName);

        Object secondObj = new BeanWrapperImpl(value)
                .getPropertyValue(secondFieldName);

        if (firstObj != null) {
            return firstObj.equals(secondObj);
        } else {
            return secondObj == null;
        }
    }
}
