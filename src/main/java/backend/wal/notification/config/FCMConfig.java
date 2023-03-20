package backend.wal.notification.config;

import backend.wal.support.YamlPropertySourceFactory;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
@PropertySource(
        value = "classpath:wal-backend-config/application-firebase.yml",
        factory = YamlPropertySourceFactory.class,
        ignoreResourceNotFound = true)
public class FCMConfig {

    private static final String FIREBASE_APP_NAME = "WAL_BACKEND";

    @Value("${firebase.fcm.config.path}")
    private String firebaseConfigPath;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        ClassPathResource firebaseResource = new ClassPathResource(firebaseConfigPath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(firebaseResource.getInputStream());
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options, FIREBASE_APP_NAME);
        }
        return FirebaseApp.getInstance(FIREBASE_APP_NAME);
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        return FirebaseMessaging.getInstance(firebaseApp());
    }
}
