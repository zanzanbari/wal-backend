package backend.wal.wal.todaywal.application.service;

import backend.wal.wal.todaywal.domain.aggregate.ShowStatus;
import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
import backend.wal.wal.todaywal.exception.NotFoundTodayWalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateTodayWalServiceTest {

    private static final Long TODAY_WAL_ID = 1L;
    private static final Long USER_ID = 1L;

    @Mock
    private TodayWalRepository todayWalRepository;

    @InjectMocks
    private UpdateTodayWalService updateTodayWalService;

    @DisplayName("todayWalId 와 userId 로 찾은 TodayWal 이 존재하지 않으면 에러가 발생한다")
    @Test
    void fail_updateShowStatus() {
        // given
        when(todayWalRepository.findTodayWalByIdAndUserId(TODAY_WAL_ID, USER_ID))
                .thenReturn(Optional.empty()); // isNull() 하면 에러

        // when, then
        assertThatThrownBy(() -> updateTodayWalService.updateShowStatus(TODAY_WAL_ID, USER_ID))
                .isInstanceOf(NotFoundTodayWalException.class)
                .hasMessage(String.format("유저 (%s)의 해당 왈소리 (%s)가 존재하지 않습니다", USER_ID, TODAY_WAL_ID));
    }

    @DisplayName("updateShowStatus 을 호출하면 todayWal 의 showStatus 가 OPEN 상태로 변한다")
    @Test
    void updateShowStatus() {
        // given
        TodayWal todayWal = TodayWal.builder().build();
        when(todayWalRepository.findTodayWalByIdAndUserId(TODAY_WAL_ID, USER_ID))
                .thenReturn(Optional.of(todayWal));

        // when
        updateTodayWalService.updateShowStatus(TODAY_WAL_ID, USER_ID);

        // then
        assertThat(todayWal.getShowStatus()).isEqualTo(ShowStatus.OPEN);
    }
}