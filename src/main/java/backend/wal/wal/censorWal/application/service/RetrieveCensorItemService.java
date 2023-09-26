package backend.wal.wal.censorWal.application.service;

import backend.wal.support.annotation.AppService;
import backend.wal.wal.censorWal.domain.aggregate.CheckStatus;
import backend.wal.wal.censorWal.application.port.in.dto.RetrieveCensorItemResponseDto;
import backend.wal.wal.censorWal.application.port.in.dto.RetrieveCensorItemRequestDto;
import backend.wal.wal.censorWal.application.port.in.RetrieveCensorItemUseCase;
import backend.wal.wal.censorWal.domain.repository.CensorItemRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AppService
@Transactional(readOnly = true)
public class RetrieveCensorItemService implements RetrieveCensorItemUseCase {

    private final CensorItemRepository censorItemRepository;

    public RetrieveCensorItemService(final CensorItemRepository censorItemRepository) {
        this.censorItemRepository = censorItemRepository;
    }

    @Override
    public List<RetrieveCensorItemResponseDto> retrieveCensorItemInfo(RetrieveCensorItemRequestDto requestDto) {
        return censorItemRepository.findAllByCheckStatus(requestDto.getCategoryType(), CheckStatus.UNCHECKED)
                .stream()
                .map(censorItem -> new RetrieveCensorItemResponseDto(
                        censorItem.getId(),
                        censorItem.getCategoryTypeName(),
                        censorItem.getContents())
                )
                .collect(Collectors.toList());
    }
}
