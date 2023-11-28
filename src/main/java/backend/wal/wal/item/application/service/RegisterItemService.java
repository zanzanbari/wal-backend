package backend.wal.wal.item.application.service;

import backend.wal.support.annotation.AppService;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.application.port.in.RegisterItemRequestDto;
import backend.wal.wal.item.application.port.in.RegisterItemUseCase;
import backend.wal.wal.item.application.port.out.ItemPersistencePort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AppService
public class RegisterItemService implements RegisterItemUseCase {

    private final ItemPersistencePort itemPersistencePort;

    public RegisterItemService(ItemPersistencePort itemPersistencePort) {
        this.itemPersistencePort = itemPersistencePort;
    }

    @Override
    @Transactional
    public void registerNewItems(List<RegisterItemRequestDto> requestDtos, WalCategoryType categoryType) {
        itemPersistencePort.saveAll(requestDtos, categoryType);
    }
}
