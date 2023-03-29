package backend.wal.onboard.domain.todaywal.service;

import backend.wal.onboard.application.port.RegisterReservationTodayWalUseCase;
import backend.wal.onboard.domain.common.WalCategoryType;
import backend.wal.onboard.domain.common.WalTimeType;
import backend.wal.onboard.domain.todaywal.aggregate.TodayWal;
import backend.wal.onboard.domain.todaywal.repository.TodayWalRepository;
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
