package com.example.travel_project.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void firestore() throws IOException {
        InputStream serviceAccount;

        String activeProfile = System.getenv("SPRING_PROFILES_ACTIVE");
        // 로컬 개발: 클래스패스에서 읽기
        if ("dev".equals(activeProfile)) {
            serviceAccount = getClass().getClassLoader().getResourceAsStream("serviceAccountKey.json");
            System.out.println("local test");
        }
        // 배포 환경: 컨테이너 절대경로에서 읽기
        else {
            serviceAccount = new FileInputStream("/serviceAccountKey.json");
            System.out.println("prod test");
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}