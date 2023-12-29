package backend.wal.wal.nextwal.adapter.out.persistence;

import backend.wal.wal.common.TestItemInitializer;
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

        List<NextWalEntity> all = nextWalRepository.findAll();
        System.out.println("size : " + all.size());
        for (NextWalEntity nextWalEntity : all) {
            System.out.println("=============================");
            System.out.println(nextWalEntity.getId());
            System.out.println(nextWalEntity.getUserId());
            System.out.println(nextWalEntity.getCategoryType());
            System.out.println(nextWalEntity.getItemId());
            System.out.println("=============================");
        }

        // when
        nextWalRepository.saveAllInBatch(List.of(
                new NextWalEntity(USER_ID, COMEDY, getComedyItemId()),
                new NextWalEntity(USER_ID, FUSS, getFussItemId()),
                new NextWalEntity(USER_ID, COMFORT, getComfortItemId()),
                new NextWalEntity(USER_ID, YELL, getYellItemId())
        ));

        System.out.println("size : " + all.size());
        for (NextWalEntity nextWalEntity : all) {
            System.out.println("=============================");
            System.out.println(nextWalEntity.getId());
            System.out.println(nextWalEntity.getUserId());
            System.out.println(nextWalEntity.getCategoryType());
            System.out.println(nextWalEntity.getItemId());
            System.out.println("=============================");
        }

        // then
        List<NextWalAndItem> nextWalAndItems = nextWalRepository.findNextWalsByUserId(USER_ID);
        for (NextWalAndItem nextWalAndItem : nextWalAndItems) {
            NextWalAndItem.ItemAttributes itemAttributes = nextWalAndItem.getItemAttributes();
            switch (nextWalAndItem.getCategoryType()) {
                case COMEDY: assertThat(itemAttributes.getId()).isEqualTo(getComedyItemId()); break;
                case FUSS: assertThat(itemAttributes.getId()).isEqualTo(getFussItemId()); break;
                case COMFORT: assertThat(itemAttributes.getId()).isEqualTo(getComfortItemId()); break;
                case YELL: assertThat(itemAttributes.getId()).isEqualTo(getYellItemId()); break;
            }
        }
    }
}