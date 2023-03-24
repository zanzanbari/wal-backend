package backend.wal.wal.service;

import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.onboarding.domain.entity.WalTimeType;
import backend.wal.wal.domain.entity.TodayWal;
import backend.wal.wal.domain.repository.TodayWalRepository;
import backend.wal.wal.exception.NotFoundTodayWalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodayWalService {

    private final TodayWalRepository todayWalRepository;

    @Transactional
    public void registerReservationTodayWal(Long userId, String message) {
        TodayWal todayWal = TodayWal.builder()
                .userId(userId)
                .message(message)
                .categoryType(WalCategoryType.RESERVATION)
                .timeType(WalTimeType.RESERVATION)
                .build();
        todayWalRepository.save(todayWal);
    }

    @Transactional
    public void updateShowStatus(Long todayWalId, Long userId) {
        TodayWal todayWal = todayWalRepository.findTodayWalByIdAndUserId(todayWalId, userId)
                .orElseThrow(() -> NotFoundTodayWalException.notExist(todayWalId, userId));
        todayWal.updateShowStatus();
    }
}
