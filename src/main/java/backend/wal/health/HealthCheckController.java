package backend.wal.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Wal Server is Running");
    }
}
