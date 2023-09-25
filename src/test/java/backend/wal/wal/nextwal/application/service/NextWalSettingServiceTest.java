package backend.wal.wal.nextwal.application.service;

import backend.wal.wal.nextwal.domain.NextWals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static backend.wal.wal.common.domain.WalCategoryType.*;
import static backend.wal.wal.common.domain.WalCategoryType.YELL;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NextWalSettingServiceTest {

    private static final Long USER_ID = 1L;

    @Autowired
    private NextWalSettingService nextWalSettingService;

    @DisplayName("")
    @Test
    void setNextWals() {
        // given


        // when
        NextWals save = nextWalSettingService.setNextWals(Set.of(COMEDY, FUSS, COMFORT, YELL), USER_ID);

        // then
    }

    @DisplayName("")
    @Test
    void updateNextWal() {
        // given
        NextWals save = nextWalSettingService.setNextWals(Set.of(COMEDY, FUSS, COMFORT, YELL), USER_ID);

        // when

        // then
    }

    @DisplayName("")
    @Test
    void deleteNextWalsByCanceledCategoryTypes() {
        // given
        NextWals save = nextWalSettingService.setNextWals(Set.of(COMEDY, FUSS, COMFORT, YELL), USER_ID);

        // when

        // then
    }
}