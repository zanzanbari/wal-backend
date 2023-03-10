package backend.wal.wal.domain;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomRangeGeneratorImpl implements RandomRangeGenerator {

    private static final Random RANDOM = new Random();

    @Override
    public int generate(int range) {
        return RANDOM.nextInt(range);
    }
}
