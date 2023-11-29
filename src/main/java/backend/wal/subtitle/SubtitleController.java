package backend.wal.subtitle;

import backend.wal.support.annotation.Authentication;

import backend.wal.wal.nextwal.domain.RandomRangeGenerator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/subtitle")
public class SubtitleController {

    private final RandomRangeGenerator randomRangeGenerator;

    public SubtitleController(final RandomRangeGenerator randomRangeGenerator) {
        this.randomRangeGenerator = randomRangeGenerator;
    }

    @Authentication
    @GetMapping
    public ResponseEntity<HomeSubtitleResponse> retrieveSubtitle() {
        int randomNumberInRange = randomRangeGenerator.generate(Subtitle.values().length - 1);
        return ResponseEntity.ok(new HomeSubtitleResponse(Subtitle.getRandomValue(randomNumberInRange)));
    }
}
