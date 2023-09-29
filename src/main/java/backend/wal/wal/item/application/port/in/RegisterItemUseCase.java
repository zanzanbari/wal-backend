package backend.wal.wal.item.application.port.in;

import backend.wal.wal.common.domain.WalCategoryType;

import java.util.List;

public interface RegisterItemUseCase {
    void registerNewItems(List<RegisterItemRequestDto> requestDtos, WalCategoryType categoryType);
}
