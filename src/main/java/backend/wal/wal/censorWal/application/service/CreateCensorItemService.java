package backend.wal.wal.censorWal.application.service;

import backend.wal.support.annotation.AppService;
import backend.wal.wal.censorWal.application.port.in.dto.CreateCensorItemRequestDto;
import backend.wal.wal.censorWal.application.port.in.CreateCensorItemUseCase;
import backend.wal.wal.censorWal.domain.aggregate.CensorItem;
import backend.wal.wal.censorWal.domain.repository.CensorItemRepository;
import org.springframework.transaction.annotation.Transactional;

@AppService
public class CreateCensorItemService implements CreateCensorItemUseCase {

    private final CensorItemRepository censorItemRepository;

    public CreateCensorItemService(final CensorItemRepository censorItemRepository) {
        this.censorItemRepository = censorItemRepository;
    }

    @Override
    @Transactional
    public void create(CreateCensorItemRequestDto requestDto) {
        CensorItem censorItem = new CensorItem(requestDto.getCategoryType(), requestDto.getContents(), null);
        censorItemRepository.save(censorItem);
    }
}
