package com.example.food_corner_v2_java.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.utils.CloudinaryImage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class CloudinaryService {

    private static final String TEMP_FILE = "temp-file";
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public CloudinaryImage uploadImage(MultipartFile multipartFile) throws IOException {

        File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);
        try {
            var uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());
            return new CloudinaryImage(
                    uploadResult.get("url").toString(),
                    uploadResult.get("public_id").toString()
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while uploading image");
        } finally {
            tempFile.delete();
        }
    }

    public boolean deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while deleting a image");
        }
    }


}
