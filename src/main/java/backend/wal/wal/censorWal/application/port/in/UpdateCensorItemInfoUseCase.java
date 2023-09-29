package backend.wal.wal.censorWal.application.port.in;

import backend.wal.wal.censorWal.domain.aggregate.CheckStatus;

public interface UpdateCensorItemInfoUseCase {
    void updateCheckStatus(Long censorItemId, CheckStatus checkStatus);
}
