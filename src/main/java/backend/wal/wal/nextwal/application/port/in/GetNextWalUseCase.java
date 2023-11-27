package backend.wal.wal.nextwal.application.port.in;

import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.NextWal;

public interface GetNextWalUseCase {

    NextWal getRandomNextWal(NextWals nextWals);

    NextWals getNextWalsByUserId(Long userId);
}
