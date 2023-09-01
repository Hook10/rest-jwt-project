package com.hook.restjwtproject.validator;

import com.hook.restjwtproject.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserExistsValidator implements ConstraintValidator<UserExists, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.isUserExists(username);
    }
}
