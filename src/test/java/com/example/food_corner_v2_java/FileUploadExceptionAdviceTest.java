package com.example.food_corner_v2_java;

import com.example.food_corner_v2_java.errors.FileUploadExceptionAdvice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class FileUploadExceptionAdviceTest {

    @InjectMocks
    private FileUploadExceptionAdvice fileUploadExceptionAdvice;

    @Mock
    private MaxUploadSizeExceededException maxUploadSizeExceededException;

    @Test
    public void testHandleMaxSizeException() {
        // Arrange
        String errorMessage = "Maximum upload size exceeded";
        when(maxUploadSizeExceededException.getMessage()).thenReturn(errorMessage);

        // Act
        ResponseEntity<Map<String, String>> responseEntity = fileUploadExceptionAdvice.handleMaxSizeException(maxUploadSizeExceededException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody().get("message"));
    }
}
