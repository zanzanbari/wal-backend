package backend.wal.config;

import backend.wal.config.jpa.JPAConfig;
import backend.wal.support.YamlPropertySourceFactory;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@PropertySource(
//        value = "classpath:wal-backend-config/application-test.yml",
//        factory = YamlPropertySourceFactory.class,
//        ignoreResourceNotFound = true)
//@DataJpaTest(properties = {"spring.config.location=classpath:wal-backend-config/application-test.yml"})
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JPAConfig.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface JpaRepositoryTestConfig {
}
