package backend.wal.wal.item.application.service;

import backend.wal.support.annotation.AppService;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.application.port.in.RegisterItemRequestDto;
import backend.wal.wal.item.application.port.in.RegisterItemUseCase;
import backend.wal.wal.item.domain.aggregate.Category;
import backend.wal.wal.item.domain.aggregate.Item;
import backend.wal.wal.item.domain.repository.CategoryRepository;
import backend.wal.wal.item.domain.repository.ItemRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AppService
public class RegisterItemService implements RegisterItemUseCase {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public RegisterItemService(final ItemRepository itemRepository, final CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void registerNewItems(List<RegisterItemRequestDto> requestDtos, WalCategoryType categoryType) {
        Category category = categoryRepository.findByCategoryType(categoryType);
        List<Item> newItems = requestDtos.stream()
                .map(requestDto -> new Item(
                        category,
                        requestDto.getContents(),
                        requestDto.getImageUrl(),
                        requestDto.getCategoryItemNumber())
                )
                .collect(Collectors.toUnmodifiableList());
        itemRepository.saveAll(newItems);
    }
}
