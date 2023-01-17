package com.example.food_corner_v2_java.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryConfig {

    private String cloudName;
    private String apiKey;
    private String apiSecret;

    public String getCloudName() {
        return cloudName;
    }

    /**
     * Sets the cloud name for the cloudinary account
     * @param cloudName the cloudName to set
     * @return this
     */
    public CloudinaryConfig setCloudName(String cloudName) {
        this.cloudName = cloudName;
        return this;
    }

    public String getApiKey() {
        return apiKey;
    }

    public CloudinaryConfig setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public CloudinaryConfig setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
        return this;
    }
}
