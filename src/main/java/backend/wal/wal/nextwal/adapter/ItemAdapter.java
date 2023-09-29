package backend.wal.wal.nextwal.adapter;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.item.application.port.in.CountItemUseCase;
import backend.wal.wal.item.application.port.in.RetrieveItemUseCase;
import backend.wal.wal.item.domain.aggregate.Item;
import backend.wal.wal.nextwal.application.port.out.ItemPort;
import org.springframework.stereotype.Component;

@Component
public class ItemAdapter implements ItemPort {

    private final RetrieveItemUseCase retrieveItemUseCase;
    private final CountItemUseCase countItemUseCase;

    public ItemAdapter(final RetrieveItemUseCase retrieveItemUseCase, final CountItemUseCase countItemUseCase) {
        this.retrieveItemUseCase = retrieveItemUseCase;
        this.countItemUseCase = countItemUseCase;
    }

    @Override
    public Item retrieveFirstByCategoryType(WalCategoryType categoryType) {
        return retrieveItemUseCase.retrieveFirstByCategoryType(categoryType);
    }

    @Override
    public Item retrieveNextItemByCategoryTypeAndNextItemId(WalCategoryType categoryType, double nextItemId) {
        return retrieveItemUseCase.retrieveNextItemByCategoryTypeAndNextItemId(categoryType, nextItemId);
    }

    @Override
    public Long countAllCorrespondItemsByCategoryType(WalCategoryType categoryType) {
        return countItemUseCase.countAllCorrespondItemsByCategoryType(categoryType);
    }
}
