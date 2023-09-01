package com.hook.restjwtproject.validator;

import static com.hook.restjwtproject.utils.Constants.USER_ALREADY_EXISTS;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UserExistsValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExists {

    String message() default USER_ALREADY_EXISTS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
