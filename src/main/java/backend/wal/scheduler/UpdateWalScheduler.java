package backend.wal.scheduler;

import backend.wal.reservation.application.port.in.ReservationRetrieveUseCase;
import backend.wal.wal.nextwal.application.port.GetNextWalUseCase;
import backend.wal.wal.onboarding.domain.aggregate.Onboarding;
import backend.wal.wal.onboarding.domain.repository.OnboardingRepository;
import backend.wal.wal.todaywal.application.port.in.RegisterReservationTodayWalUseCase;
import backend.wal.wal.todaywal.application.port.in.TodayWalSettingUseCase;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Component
public final class UpdateWalScheduler {

    private final OnboardingRepository onboardingRepository;
    private final TodayWalRepository todayWalRepository;
    private final GetNextWalUseCase getNextWalUseCase;
    private final TodayWalSettingUseCase todayWalSettingUseCase;
    private final ReservationRetrieveUseCase reservationRetrieveUseCase;
    private final RegisterReservationTodayWalUseCase registerReservationTodayWalUseCase;

    public UpdateWalScheduler(final OnboardingRepository onboardingRepository,
                              final TodayWalRepository todayWalRepository,
                              final GetNextWalUseCase getNextWalUseCase,
                              final TodayWalSettingUseCase todayWalSettingUseCase,
                              final ReservationRetrieveUseCase reservationRetrieveUseCase,
                              final RegisterReservationTodayWalUseCase registerReservationTodayWalUseCase) {
        this.onboardingRepository = onboardingRepository;
        this.todayWalRepository = todayWalRepository;
        this.getNextWalUseCase = getNextWalUseCase;
        this.todayWalSettingUseCase = todayWalSettingUseCase;
        this.reservationRetrieveUseCase = reservationRetrieveUseCase;
        this.registerReservationTodayWalUseCase = registerReservationTodayWalUseCase;
    }

    @Scheduled(cron = "0 0 0 * * *")
    void updateWalAtNoonEveryday() {
        todayWalRepository.deleteAllInBatch();
        List<Onboarding> onboardings = onboardingRepository.findAll();
        for (Onboarding onboarding : onboardings) {
            Long userId = onboarding.getUserId();
            todayWalSettingUseCase.setTodayWals(onboarding.getTimeTypes(), userId,
                    getNextWalUseCase.getNextWalsByUserId(userId));

            reservationRetrieveUseCase.retrieveReservationBetweenTodayAndTomorrow(userId)
                    .ifPresent(reservation -> registerReservationTodayWalUseCase
                            .registerReservationTodayWal(userId, reservation.getMessage()));
        }
    }
}