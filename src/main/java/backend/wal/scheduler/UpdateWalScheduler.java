package backend.wal.scheduler;

import backend.wal.reservation.application.port.in.ReservationRetrieveUseCase;
import backend.wal.wal.nextwal.application.port.out.NextWalPersistencePort;
import backend.wal.wal.nextwal.domain.NextWal;
import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.onboarding.domain.aggregate.Onboarding;
import backend.wal.wal.onboarding.domain.repository.OnboardingRepository;
import backend.wal.wal.todaywal.application.port.in.ReservationTodayWalHandlerUseCase;
import backend.wal.wal.todaywal.application.port.in.TodayWalSettingUseCase;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class UpdateWalScheduler {

    private final OnboardingRepository onboardingRepository;
    private final TodayWalRepository todayWalRepository;
    private final NextWalPersistencePort nextWalPersistencePort;
    private final TodayWalSettingUseCase todayWalSettingUseCase;
    private final ReservationRetrieveUseCase reservationRetrieveUseCase;
    private final ReservationTodayWalHandlerUseCase reservationTodayWalHandlerUseCase;

    public UpdateWalScheduler(final OnboardingRepository onboardingRepository,
                              final TodayWalRepository todayWalRepository,
                              final NextWalPersistencePort nextWalPersistencePort,
                              final TodayWalSettingUseCase todayWalSettingUseCase,
                              final ReservationRetrieveUseCase reservationRetrieveUseCase,
                              final ReservationTodayWalHandlerUseCase reservationTodayWalHandlerUseCase) {
        this.onboardingRepository = onboardingRepository;
        this.todayWalRepository = todayWalRepository;
        this.nextWalPersistencePort = nextWalPersistencePort;
        this.todayWalSettingUseCase = todayWalSettingUseCase;
        this.reservationRetrieveUseCase = reservationRetrieveUseCase;
        this.reservationTodayWalHandlerUseCase = reservationTodayWalHandlerUseCase;
    }

    @Scheduled(cron = "0 0 0 * * *")
    void updateWalAtNoonEveryday() {
        todayWalRepository.deleteAllInBatch();

        List<Onboarding> onboardings = onboardingRepository.findOnboardingsWithTimeTypes();
        for (Onboarding onboarding : onboardings) {
            Long userId = onboarding.getUserId();
            List<NextWal> nextWalWithItem = nextWalPersistencePort.findNextWalsByUserId(userId);
            todayWalSettingUseCase.setTodayWals(onboarding.getTimeTypes(), userId, new NextWals(nextWalWithItem));

            reservationRetrieveUseCase.retrieveReservationBetweenTodayAndTomorrow(userId)
                    .ifPresent(reservation -> reservationTodayWalHandlerUseCase
                            .registerReservationTodayWal(userId, reservation.getMessage(), reservation.getSendDueDate()));
        }
    }
}
