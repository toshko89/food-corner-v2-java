package com.example.food_corner_v2_java;

import com.example.food_corner_v2_java.errors.AppException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppExceptionTest {

    @Test
    public void testAppException() {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = "Test error message";

        AppException appException = new AppException(httpStatus, errorMessage);

        assertEquals(httpStatus, appException.getHttpStatus());
        assertEquals(errorMessage, appException.getMessage());
    }
}
