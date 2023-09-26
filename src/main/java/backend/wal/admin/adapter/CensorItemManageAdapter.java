package backend.wal.admin.adapter;

import backend.wal.admin.web.dto.RetrieveCensorItemResponse;
import backend.wal.admin.web.dto.RetrieveCensorItemRequest;
import backend.wal.admin.application.port.out.CensorItemManagePort;
import backend.wal.wal.censorWal.application.port.in.RetrieveCensorItemUseCase;
import backend.wal.wal.censorWal.application.port.in.UpdateCensorItemInfoUseCase;
import backend.wal.wal.censorWal.application.port.in.dto.RetrieveCensorItemRequestDto;
import backend.wal.wal.censorWal.domain.aggregate.CheckStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CensorItemManageAdapter implements CensorItemManagePort {

    private final RetrieveCensorItemUseCase retrieveCensorItemUseCase;
    private final UpdateCensorItemInfoUseCase updateCensorItemInfoUseCase;

    public CensorItemManageAdapter(final RetrieveCensorItemUseCase retrieveCensorItemUseCase,
                                   final UpdateCensorItemInfoUseCase updateCensorItemInfoUseCase) {
        this.retrieveCensorItemUseCase = retrieveCensorItemUseCase;
        this.updateCensorItemInfoUseCase = updateCensorItemInfoUseCase;
    }

    @Override
    public List<RetrieveCensorItemResponse> retrieveCensorItemInfo(RetrieveCensorItemRequest request) {
        return retrieveCensorItemUseCase.retrieveCensorItemInfo(new RetrieveCensorItemRequestDto(request.getCategoryType()))
                .stream()
                .map(responseDto -> new RetrieveCensorItemResponse(
                        responseDto.getCensorItemId(),
                        responseDto.getWalCategoryType(),
                        responseDto.getContents())
                )
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void updateCheckStatus(Long censorItemId, CheckStatus checkStatus) {
        updateCensorItemInfoUseCase.updateCheckStatus(censorItemId, checkStatus);
    }
}
