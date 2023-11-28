package backend.wal.wal.nextwal.adapter.out.persistence;

import backend.wal.wal.common.TestItemInitializer;
import backend.wal.wal.item.adapter.out.persistence.ItemEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static backend.wal.wal.common.domain.WalCategoryType.*;
import static org.assertj.core.api.Assertions.assertThat;

class NextWalRepositoryTest extends TestItemInitializer {

    private static final Long USER_ID = 1L;

    @Autowired
    private NextWalRepository nextWalRepository;

    @DisplayName("userId 로 해당 유저의 NextWalEntity 정보들을 가져온다")
    @Test
    void findNextWalsByUserId() {
        // given
        setForNexWalRepositoryTest();
        nextWalRepository.saveAll(List.of(
                new NextWalEntity(USER_ID, COMEDY, getComedyItemId()),
                new NextWalEntity(USER_ID, FUSS, getFussItemId()),
                new NextWalEntity(USER_ID, COMFORT, getComfortItemId()),
                new NextWalEntity(USER_ID, YELL, getYellItemId()))
        );

        // when
        List<NextWalWithItem> nextWalWithItems = nextWalRepository.findNextWalsByUserId(USER_ID);

        // then
        for (NextWalWithItem nextWalWithItem : nextWalWithItems) {
            NextWalEntity nextWalEntity = nextWalWithItem.getNextWalEntity();
            ItemEntity itemEntity = nextWalWithItem.getItemEntity();
            assertThat(nextWalEntity.getItemId()).isEqualTo(itemEntity.getId());
            assertThat(nextWalEntity.getCategoryType()).isEqualTo(itemEntity.getCategoryType());
        }
    }

    @DisplayName("WalCategoryType 과 userId 로 해당 유저의 해당 카테고리 NextWalEntity 정보들을 가져온다")
    @Test
    void findNextWalsByCategoryTypeInAndUserId() {
        // given
        setForNexWalRepositoryTest();
        nextWalRepository.saveAll(List.of(
                new NextWalEntity(USER_ID, COMEDY, getComedyItemId()),
                new NextWalEntity(USER_ID, FUSS, getFussItemId()),
                new NextWalEntity(USER_ID, COMFORT, getComfortItemId()),
                new NextWalEntity(USER_ID, YELL, getYellItemId()))
        );

        // when
        List<NextWalEntity> find = nextWalRepository.findNextWalsByCategoryTypeInAndUserId(Set.of(COMEDY, YELL), USER_ID);

        // then
        assertThat(find).hasSize(2);
    }
}