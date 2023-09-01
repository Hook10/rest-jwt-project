package com.hook.restjwtproject.validator;

import static com.hook.restjwtproject.utils.Constants.PASSWORD_NOT_MATCHING;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PasswordMatchingValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatching {

    String message() default PASSWORD_NOT_MATCHING;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
