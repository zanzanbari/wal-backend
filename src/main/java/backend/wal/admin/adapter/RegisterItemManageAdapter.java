package backend.wal.admin.adapter;

import backend.wal.admin.application.port.out.RegisterItemManagePort;
import backend.wal.wal.censorWal.application.port.in.RetrieveCensorItemUseCase;
import backend.wal.wal.censorWal.application.port.in.dto.ApprovedCensorItemResponseDto;
import backend.wal.wal.censorWal.application.port.in.dto.RetrieveCensorItemRequestDto;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.application.port.in.CountItemUseCase;
import backend.wal.wal.item.application.port.in.RegisterItemUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegisterItemManageAdapter implements RegisterItemManagePort {

    private final RetrieveCensorItemUseCase retrieveCensorItemUseCase;
    private final CountItemUseCase countItemUseCase;
    private final RegisterItemUseCase registerItemUseCase;

    public RegisterItemManageAdapter(final RetrieveCensorItemUseCase retrieveCensorItemUseCase,
                                     final CountItemUseCase countItemUseCase,
                                     final RegisterItemUseCase registerItemUseCase) {
        this.retrieveCensorItemUseCase = retrieveCensorItemUseCase;
        this.countItemUseCase = countItemUseCase;
        this.registerItemUseCase = registerItemUseCase;
    }

    @Override
    public void registerItemsBy(WalCategoryType categoryType) {
        List<ApprovedCensorItemResponseDto> approvedCensorItemInfo = retrieveCensorItemUseCase
                .retrieveApprovedCensorItemInfo(new RetrieveCensorItemRequestDto(categoryType));
        Long countOfCategoryType = countItemUseCase.countAllCorrespondItemsByCategoryType(categoryType);
        RegisterItemRequestDtoConvertor registerItemRequestDtoConvertor =
                new RegisterItemRequestDtoConvertor(approvedCensorItemInfo, countOfCategoryType);
        registerItemUseCase.registerNewItems(registerItemRequestDtoConvertor.convert(), categoryType);
    }
}
