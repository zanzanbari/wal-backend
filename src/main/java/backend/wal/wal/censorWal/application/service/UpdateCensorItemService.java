package backend.wal.wal.censorWal.application.service;

import backend.wal.support.annotation.AppService;
import backend.wal.wal.censorWal.application.port.in.UpdateCensorItemInfoUseCase;
import backend.wal.wal.censorWal.domain.aggregate.CheckStatus;
import backend.wal.wal.censorWal.domain.repository.CensorItemRepository;
import org.springframework.transaction.annotation.Transactional;

@AppService
public class UpdateCensorItemService implements UpdateCensorItemInfoUseCase {

    private final CensorItemRepository censorItemRepository;

    public UpdateCensorItemService(final CensorItemRepository censorItemRepository) {
        this.censorItemRepository = censorItemRepository;
    }

    @Override
    @Transactional
    public void updateCheckStatus(Long censorItemId, CheckStatus checkStatus) {
        censorItemRepository.updateCheckStatus(censorItemId, checkStatus);
    }
}
