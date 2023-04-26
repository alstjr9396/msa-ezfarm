package me.minseok.ezfarmfarm.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<EnumValue, CharSequence> {

    private List<String> enumNames;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        Enum<?>[] enumConstants = constraintAnnotation.enumClass().getEnumConstants();
        enumNames = Arrays.stream(enumConstants).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return enumNames.contains(String.valueOf(value));
    }
}
