package com.example.food_corner_v2_java.utils;

public class CloudinaryImage {
    private String url;
    private String publicId;

    public CloudinaryImage(String url, String publicId) {
        this.url = url;
        this.publicId = publicId;
    }

    public String getUrl() {
        return url;
    }

    public CloudinaryImage setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublicId() {
        return publicId;
    }

    public CloudinaryImage setPublicId(String publicId) {
        this.publicId = publicId;
        return this;
    }
}
