package backend.wal.wal.censorWal.application.service;

import backend.wal.support.annotation.AppService;
import backend.wal.wal.censorWal.application.port.in.RetrieveCensorItemUseCase;
import backend.wal.wal.censorWal.application.port.in.dto.ApprovedCensorItemResponseDto;
import backend.wal.wal.censorWal.application.port.in.dto.RetrieveCensorItemRequestDto;
import backend.wal.wal.censorWal.application.port.in.dto.UncheckedCensorItemResponseDto;
import backend.wal.wal.censorWal.domain.repository.CensorItemRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static backend.wal.wal.censorWal.domain.aggregate.CheckStatus.APPROVED;
import static backend.wal.wal.censorWal.domain.aggregate.CheckStatus.UNCHECKED;

@AppService
@Transactional(readOnly = true)
public class RetrieveCensorItemService implements RetrieveCensorItemUseCase {

    private final CensorItemRepository censorItemRepository;

    public RetrieveCensorItemService(final CensorItemRepository censorItemRepository) {
        this.censorItemRepository = censorItemRepository;
    }

    @Override
    public List<UncheckedCensorItemResponseDto> retrieveUncheckedCensorItemInfo(
            RetrieveCensorItemRequestDto requestDto) {
        return censorItemRepository
                .findAllByCategoryTypeAndCheckStatus(requestDto.getCategoryType(), UNCHECKED)
                .stream()
                .map(censorItem -> new UncheckedCensorItemResponseDto(
                        censorItem.getId(),
                        censorItem.getCategoryTypeName(),
                        censorItem.getContents())
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<ApprovedCensorItemResponseDto> retrieveApprovedCensorItemInfo(
            RetrieveCensorItemRequestDto requestDto) {
        return censorItemRepository
                .findAllByCategoryTypeAndCheckStatus(requestDto.getCategoryType(), APPROVED)
                .stream()
                .map(censorItem -> new ApprovedCensorItemResponseDto(
                        censorItem.getCategoryType(),
                        censorItem.getContents(),
                        censorItem.getImageUrl())
                )
                .collect(Collectors.toList());
    }
}
