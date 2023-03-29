package backend.wal.wal.todaywal.application.service;

import backend.wal.wal.todaywal.application.port.RegisterReservationTodayWalUseCase;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.support.annotation.AppService;

@AppService
public class RegisterReservationTodayWalService implements RegisterReservationTodayWalUseCase {

    private final TodayWalRepository todayWalRepository;

    public RegisterReservationTodayWalService(final TodayWalRepository todayWalRepository) {
        this.todayWalRepository = todayWalRepository;
    }

    @Override
    public void registerReservationTodayWal(Long userId, String message) {
        TodayWal todayWal = TodayWal.builder()
                .userId(userId)
                .message(message)
                .categoryType(WalCategoryType.RESERVATION)
                .timeType(WalTimeType.RESERVATION)
                .build();
        todayWalRepository.save(todayWal);
    }
}
