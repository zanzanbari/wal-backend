package backend.wal.wal.nextwal.adapter.out.persistence;

import backend.wal.config.JpaRepositoryTestConfig;
import backend.wal.wal.common.TestItemInitializer;
import backend.wal.wal.item.adapter.out.persistence.ItemEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static backend.wal.wal.common.domain.WalCategoryType.*;
import static backend.wal.wal.common.domain.WalCategoryType.YELL;
import static org.assertj.core.api.Assertions.assertThat;

class NextWalRepositoryCustomImplTest extends TestItemInitializer {

    private static final Long USER_ID = 1L;

    @Autowired
    private NextWalRepository nextWalRepository;

    @DisplayName("saveAllInBatch 를 통해 한번의 쿼리로 대량의 데이터를 insert 한다")
    @Test
    void saveAllInBatch() {
        // given
        setForNexWalRepositoryTest();

        // when
        nextWalRepository.saveAllInBatch(List.of(
                new NextWalEntity(USER_ID, COMEDY, getComedyItemId()),
                new NextWalEntity(USER_ID, FUSS, getFussItemId()),
                new NextWalEntity(USER_ID, COMFORT, getComfortItemId()),
                new NextWalEntity(USER_ID, YELL, getYellItemId())
        ));

        // then
        List<NextWalWithItem> nextWalWithItems = nextWalRepository.findNextWalsByUserId(USER_ID);
        for (NextWalWithItem nextWalWithItem : nextWalWithItems) {
            NextWalEntity nextWalEntity = nextWalWithItem.getNextWalEntity();
            ItemEntity itemEntity = nextWalWithItem.getItemEntity();
            assertThat(nextWalEntity.getItemId()).isEqualTo(itemEntity.getId());
            assertThat(nextWalEntity.getCategoryType()).isEqualTo(itemEntity.getCategoryType());
        }
    }
}