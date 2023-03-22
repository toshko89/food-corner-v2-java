package com.example.food_corner_v2_java;

import com.example.food_corner_v2_java.service.AppUserService;
import com.example.food_corner_v2_java.validation.UniqueUserNameValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniqueUserNameValidatorTest {

    @InjectMocks
    private UniqueUserNameValidator uniqueUserNameValidator;

    @Mock
    private AppUserService appUserService;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testIsValidWhenUserNameIsNotTaken() {
        String userName = "availableUsername";
        when(appUserService.userNameIsTaken(userName)).thenReturn(null);

        boolean isValid = uniqueUserNameValidator.isValid(userName, constraintValidatorContext);

        assertTrue(isValid, "Expected isValid to be true when username is not taken");
    }
}
