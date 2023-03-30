package backend.wal.wal.nextwal.application.service;

import backend.wal.support.annotation.DomainService;
import backend.wal.wal.nextwal.application.port.GetNextWalUseCase;
import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.aggregate.NextWal;
import backend.wal.wal.nextwal.domain.repository.NextWalRepository;
import backend.wal.wal.nextwal.domain.support.RandomRangeGenerator;

@DomainService
public class GetNextWalService implements GetNextWalUseCase {

    private final RandomRangeGenerator randomRangeGenerator;
    private final NextWalRepository nextWalRepository;

    public GetNextWalService(final RandomRangeGenerator randomRangeGenerator, final NextWalRepository nextWalRepository) {
        this.randomRangeGenerator = randomRangeGenerator;
        this.nextWalRepository = nextWalRepository;
    }

    @Override
    public NextWal getRandomNextWal(NextWals nextWals) {
        return nextWals.getRandomBy(randomRangeGenerator);
    }

    @Override
    public NextWals getNextWalsByUserId(Long userId) {
        return new NextWals(nextWalRepository.findNextWalsByUserId(userId));
    }
}
