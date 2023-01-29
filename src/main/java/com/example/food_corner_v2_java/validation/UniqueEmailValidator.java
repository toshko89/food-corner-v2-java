package com.example.food_corner_v2_java.validation;

import com.example.food_corner_v2_java.service.AppUserService;
import com.example.food_corner_v2_java.validation.annotations.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final AppUserService appUserService;

    @Autowired
    public UniqueEmailValidator(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.appUserService.emailIsTaken(value) == null;
    }
}
