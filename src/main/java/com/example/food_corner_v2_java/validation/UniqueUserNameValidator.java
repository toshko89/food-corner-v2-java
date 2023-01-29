package com.example.food_corner_v2_java.validation;

import com.example.food_corner_v2_java.service.AppUserService;
import com.example.food_corner_v2_java.validation.annotations.UniqueUserName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName,String> {

    private final AppUserService appUserService;

    @Autowired
    public UniqueUserNameValidator(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.appUserService.userNameIsTaken(value) == null;
    }
}
