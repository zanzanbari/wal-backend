package backend.wal.config;

import backend.wal.config.jpa.JPAConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ActiveProfiles("test")
@DataJpaTest(properties = {"spring.config.location = classpath:wal-backend-config/application-test.yml"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JPAConfig.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface JpaRepositoryTestConfig {
}
