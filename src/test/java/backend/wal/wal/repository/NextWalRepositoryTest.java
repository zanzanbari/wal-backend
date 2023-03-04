package backend.wal.wal.repository;

import backend.wal.config.jpa.JPAConfig;
import backend.wal.wal.domain.NextWals;
import backend.wal.wal.domain.entity.NextWal;
import backend.wal.wal.domain.repository.NextWalRepository;
import backend.wal.wal.service.WalSettingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static backend.wal.onboarding.domain.entity.WalCategoryType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Import(JPAConfig.class)
class NextWalRepositoryTest {

    private static final Long USER_ID = 1L;

    @Autowired
    private NextWalRepository nextWalRepository;

    @Autowired
    private WalSettingService walSettingService;

    @DisplayName("userId 로 해당 유저의 NextWal 정보들을 가져온다")
    @Test
    void findNextWalsByUserId() {
        // given
        NextWals save = walSettingService.setNextWals(Set.of(COMEDY, FUSS, COMFORT, YELL), USER_ID);

        // when
        List<NextWal> find = nextWalRepository.findNextWalsByUserId(USER_ID);

        // then
        assertAll(
                () -> assertThat(find).hasSize(save.getSize()),
                () -> assertThat(find).isEqualTo(save.getValues())
        );
    }

    @DisplayName("WalCategoryType 과 userId 로 해당 유저의 해당 카테고리 NextWal 정보들을 가져온다")
    @Test
    void findNextWalsByCategoryTypeInAndUserId() {
        // given
        walSettingService.setNextWals(Set.of(COMEDY, FUSS, COMFORT, YELL), USER_ID);

        // when
        List<NextWal> find = nextWalRepository.findNextWalsByCategoryTypeInAndUserId(Set.of(COMEDY, YELL), USER_ID);

        // then
        assertThat(find).hasSize(2);
    }
}