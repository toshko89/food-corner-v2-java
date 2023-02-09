package com.example.food_corner_v2_java.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.food_corner_v2_java.errors.AppException;
import com.example.food_corner_v2_java.utils.CloudinaryImage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public CloudinaryImage uploadImage(File file) throws IOException {
        try {
            var uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            return new CloudinaryImage(
                    uploadResult.get("url").toString(),
                    uploadResult.get("public_id").toString()
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR,"Error occurred while uploading image");
        }
    }

    public boolean deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR,"Error occurred while deleting a image");
        }
    }


}
