package backend.wal.wal.nextwal.application.port.out;

import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.aggregate.NextWal;

public interface GetNextWalUseCase {

    NextWal getRandomNextWal(NextWals nextWals);

    NextWals getNextWalsByUserId(Long userId);
}
