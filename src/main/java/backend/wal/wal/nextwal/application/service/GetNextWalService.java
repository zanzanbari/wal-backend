package backend.wal.wal.nextwal.application.service;

import backend.wal.wal.nextwal.application.port.in.GetNextWalUseCase;
import backend.wal.wal.nextwal.application.port.out.NextWalPersistencePort;
import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.NextWal;
import backend.wal.wal.nextwal.domain.RandomRangeGenerator;
import backend.wal.support.annotation.DomainService;

@DomainService
public class GetNextWalService implements GetNextWalUseCase {

    private final RandomRangeGenerator randomRangeGenerator;
    private final NextWalPersistencePort nextWalPersistencePort;

    public GetNextWalService(RandomRangeGenerator randomRangeGenerator, NextWalPersistencePort nextWalPersistencePort) {
        this.randomRangeGenerator = randomRangeGenerator;
        this.nextWalPersistencePort = nextWalPersistencePort;
    }

    @Override
    public NextWal getRandomNextWal(NextWals nextWals) {
        return nextWals.getRandomBy(randomRangeGenerator);
    }

    @Override
    public NextWals getNextWalsByUserId(Long userId) {
        return new NextWals(nextWalPersistencePort.findNextWalsByUserId(userId));
    }
}
