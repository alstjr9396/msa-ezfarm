package me.minseok.ezfarmfarm.common.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValue {

    Class<? extends Enum<?>> enumClass();

    String message() default "ENUM 타입을 맞춰주세요.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
