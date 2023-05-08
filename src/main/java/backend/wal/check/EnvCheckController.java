package backend.wal.check;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class EnvCheckController {

    private final Environment environment;

    public EnvCheckController(final Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/profile")
    public String getProfile() {
        return Arrays.stream(environment.getActiveProfiles())
                .findFirst()
                .orElse("");
    }
}
