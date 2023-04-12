package backend.wal;

import backend.wal.support.YamlPropertySourceFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@PropertySource(
        value = "classpath:wal-backend-config/application-test.yml",
        factory = YamlPropertySourceFactory.class,
        ignoreResourceNotFound = true)
@SpringBootTest(properties = "spring.config.additional-location=classpath:wal-backend-config/application-test.yml")
class WalApplicationTests {

    @Test
    void contextLoads() {
    }

}
