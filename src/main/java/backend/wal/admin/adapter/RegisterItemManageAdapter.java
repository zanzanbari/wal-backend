package backend.wal.admin.adapter;

import backend.wal.admin.application.port.out.RegisterItemManagePort;
import backend.wal.wal.censorWal.application.port.in.RetrieveCensorItemUseCase;
import backend.wal.wal.censorWal.application.port.in.dto.ItemToRegisterDto;
import backend.wal.wal.censorWal.application.port.in.dto.RetrieveCensorItemRequestDto;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.application.port.in.CountItemUseCase;
import backend.wal.wal.item.application.port.in.RegisterItemUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<ItemToRegisterDto> approvedCensorItemInfo = retrieveCensorItemUseCase
                .retrieveApprovedCensorItemInfo(new RetrieveCensorItemRequestDto(categoryType));
        Long countOfCategoryType = countItemUseCase.countAllCorrespondItemsByCategoryType(categoryType);
        RegisterItemRequestDtoConvertor registerItemRequestDtoConvertor =
                new RegisterItemRequestDtoConvertor(approvedCensorItemInfo, countOfCategoryType);
        registerItemUseCase.registerNewItems(registerItemRequestDtoConvertor.convert(), categoryType);
    }

    @Override
    public void registerAllItems(Set<String> contents, WalCategoryType categoryType) {
        List<ItemToRegisterDto> itemToRegisterInfo = contents.stream()
                .map(content -> new ItemToRegisterDto(categoryType, content, ""))
                .collect(Collectors.toList());
        Long countOfCategoryType = countItemUseCase.countAllCorrespondItemsByCategoryType(categoryType);
        RegisterItemRequestDtoConvertor registerItemRequestDtoConvertor =
                new RegisterItemRequestDtoConvertor(itemToRegisterInfo, countOfCategoryType);
        registerItemUseCase.registerNewItems(registerItemRequestDtoConvertor.convert(), categoryType);
    }
}
