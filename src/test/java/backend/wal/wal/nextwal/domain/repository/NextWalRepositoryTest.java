package backend.wal.wal.nextwal.domain.repository;

import backend.wal.wal.common.TestItemInitializer;
import backend.wal.wal.nextwal.domain.aggregate.NextWal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static backend.wal.wal.common.domain.WalCategoryType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class NextWalRepositoryTest extends TestItemInitializer {

    private static final Long USER_ID = 1L;

    @Autowired
    private NextWalRepository nextWalRepository;

    @DisplayName("userId 로 해당 유저의 NextWal 정보들을 가져온다")
    @Test
    void findNextWalsByUserId() {
        // given
        setForNexWalRepositoryTest();
        List<NextWal> save = nextWalRepository.saveAll(List.of(
                NextWal.newInstance(USER_ID, COMEDY, getComedyItem()),
                NextWal.newInstance(USER_ID, FUSS, getFussItem()),
                NextWal.newInstance(USER_ID, COMFORT, getComfortItem()),
                NextWal.newInstance(USER_ID, YELL, getYellItem())
        ));

        // when
        List<NextWal> find = nextWalRepository.findNextWalsByUserId(USER_ID);

        // then
        assertAll(
                () -> assertThat(find).hasSize(save.size()),
                () -> assertThat(find).isEqualTo(save)
        );
    }

    @DisplayName("WalCategoryType 과 userId 로 해당 유저의 해당 카테고리 NextWal 정보들을 가져온다")
    @Test
    void findNextWalsByCategoryTypeInAndUserId() {
        // given
        setForNexWalRepositoryTest();
        nextWalRepository.saveAll(List.of(
                NextWal.newInstance(USER_ID, COMEDY, getComedyItem()),
                NextWal.newInstance(USER_ID, FUSS, getFussItem()),
                NextWal.newInstance(USER_ID, COMFORT, getComfortItem()),
                NextWal.newInstance(USER_ID, YELL, getYellItem())
        ));

        // when
        List<NextWal> find = nextWalRepository.findNextWalsByCategoryTypeInAndUserId(Set.of(COMEDY, YELL), USER_ID);

        // then
        assertThat(find).hasSize(2);
    }
}