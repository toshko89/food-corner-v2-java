package com.example.food_corner_v2_java.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public Bucket getFirebaseStorage() throws IOException {

        FileInputStream serviceAccount = new FileInputStream("C:\\Projects\\food_corner_v2_java\\food_corner_v2_java\\src\\main\\resources\\food-corner-project-firebase.json");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("food-corner-project.appspot.com")
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
        }

        return StorageClient.getInstance().bucket();
    }
}