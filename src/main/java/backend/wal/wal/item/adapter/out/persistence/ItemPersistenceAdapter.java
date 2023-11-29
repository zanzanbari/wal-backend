package backend.wal.wal.item.adapter.out.persistence;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.application.port.in.RegisterItemRequestDto;
import backend.wal.wal.item.application.port.out.ItemPersistencePort;
import backend.wal.wal.item.domain.Category;
import backend.wal.wal.item.domain.Item;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemPersistenceAdapter implements ItemPersistencePort {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public ItemPersistenceAdapter(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void saveAll(List<RegisterItemRequestDto> requestDtos, WalCategoryType categoryType) {
        CategoryEntity categoryEntity = categoryRepository.findByCategoryType(categoryType);
        List<ItemEntity> itemEntities = requestDtos.stream()
                .map(requestDto -> new ItemEntity(categoryEntity,
                        requestDto.getContents(),
                        requestDto.getImageUrl(),
                        requestDto.getCategoryItemNumber()
                ))
                .collect(Collectors.toList());
        itemRepository.saveAll(itemEntities);
    }

    @Override
    public List<Item> findFirstItemsByCategoryTypes(Iterable<WalCategoryType> categoryTypes) {
        return itemRepository.findFirstItemsByCategoryTypes(categoryTypes)
                .stream()
                .map(itemEntity -> Item.of(itemEntity, new Category(itemEntity.getCategoryType())))
                .collect(Collectors.toList());
    }

    @Override
    public Long countAllByCategoryCategoryType(WalCategoryType categoryType) {
        return itemRepository.countAllByCategoryCategoryType(categoryType);
    }

    @Override
    public Item findByCategoryTypeAndCategoryItemNumber(WalCategoryType categoryType, int nextItemId) {
        ItemEntity itemEntity = itemRepository.findByCategoryTypeAndCategoryItemNumber(categoryType, nextItemId);
        return Item.of(itemEntity, new Category(categoryType));
    }
}
