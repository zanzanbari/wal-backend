package backend.wal.wal.todaywal.domain.service;

import backend.wal.wal.todaywal.application.port.RegisterReservationTodayWalUseCase;
import backend.wal.wal.common.domain.aggregate.WalCategoryType;
import backend.wal.wal.common.domain.aggregate.WalTimeType;
import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
import backend.wal.support.annotation.DomainService;

@DomainService
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
