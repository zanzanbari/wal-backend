package backend.wal.wal.item.application.service;

import backend.wal.support.annotation.AppService;
import backend.wal.wal.item.application.port.in.RegisterItemUseCase;
import backend.wal.wal.item.domain.repository.ItemRepository;
import org.springframework.transaction.annotation.Transactional;

@AppService
public class RegisterItemService implements RegisterItemUseCase {

    private final ItemRepository itemRepository;

    public RegisterItemService(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public void registerNewItems() {

    }
}
