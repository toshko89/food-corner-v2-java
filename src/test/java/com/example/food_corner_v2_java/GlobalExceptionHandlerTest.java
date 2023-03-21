package com.example.food_corner_v2_java;

import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.errors.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private AppException appException;

    @Test
    public void testHandleAppException() {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = "Test error message";
        when(appException.getHttpStatus()).thenReturn(httpStatus);
        when(appException.getMessage()).thenReturn(errorMessage);

        ResponseEntity<Map<String, String>> responseEntity = globalExceptionHandler.handleAppException(appException);

        assertEquals(httpStatus, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody().get("message"));
    }
}
