package backend.wal.config;

import backend.wal.config.jpa.JPAConfig;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/test",
        "spring.datasource.username=test",
        "spring.datasource.password=test_password",
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JPAConfig.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface JpaRepositoryTestConfig {
}
