package com.example.food_corner_v2_java.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.food_corner_v2_java.service.CloudinaryService;
import com.example.food_corner_v2_java.utils.CloudinaryImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CloudinaryServiceTest {

    @InjectMocks
    private CloudinaryService cloudinaryService;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        multipartFile = new MockMultipartFile("image", "image.jpg", "image/jpeg", "test-image".getBytes());
    }

    @Test
    void uploadImageSuccess() throws IOException {
        Map<String, Object> uploadResult = new HashMap<>();
        uploadResult.put("url", "http://example.com/image.jpg");
        uploadResult.put("public_id", "test_public_id");

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(File.class), anyMap())).thenReturn(uploadResult);

        CloudinaryImage result = cloudinaryService.uploadImage(multipartFile);

        assertEquals("http://example.com/image.jpg", result.getUrl());
        assertEquals("test_public_id", result.getPublicId());
        verify(uploader, times(1)).upload(any(File.class), anyMap());
    }

    @Test
    void deleteImageSuccess() throws IOException {
        when(cloudinary.uploader()).thenReturn(uploader);
        boolean result = cloudinaryService.deleteImage("test_public_id");

        assertTrue(result);
        verify(uploader, times(1)).destroy(anyString(), anyMap());
    }
}
