package backend.wal.scheduler;

import backend.wal.reservation.application.port.in.ReservationRetrieveUseCase;
import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.aggregate.NextWal;
import backend.wal.wal.nextwal.domain.repository.NextWalRepository;
import backend.wal.wal.onboarding.domain.aggregate.Onboarding;
import backend.wal.wal.onboarding.domain.repository.OnboardingRepository;
import backend.wal.wal.todaywal.application.port.in.ReservationTodayWalHandlerUseCase;
import backend.wal.wal.todaywal.application.port.in.TodayWalSettingUseCase;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Component
public final class UpdateWalScheduler {

    private final OnboardingRepository onboardingRepository;
    private final TodayWalRepository todayWalRepository;
    private final NextWalRepository nextWalRepository;
    private final TodayWalSettingUseCase todayWalSettingUseCase;
    private final ReservationRetrieveUseCase reservationRetrieveUseCase;
    private final ReservationTodayWalHandlerUseCase reservationTodayWalHandlerUseCase;

    public UpdateWalScheduler(final OnboardingRepository onboardingRepository,
                              final TodayWalRepository todayWalRepository,
                              final NextWalRepository nextWalRepository,
                              final TodayWalSettingUseCase todayWalSettingUseCase,
                              final ReservationRetrieveUseCase reservationRetrieveUseCase,
                              final ReservationTodayWalHandlerUseCase reservationTodayWalHandlerUseCase) {
        this.onboardingRepository = onboardingRepository;
        this.todayWalRepository = todayWalRepository;
        this.nextWalRepository = nextWalRepository;
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
            List<NextWal> nextWalWithItem = nextWalRepository.findNextWalsWithItemByUserId(userId);
            todayWalSettingUseCase.setTodayWals(onboarding.getTimeTypes(), userId, new NextWals(nextWalWithItem));

            reservationRetrieveUseCase.retrieveReservationBetweenTodayAndTomorrow(userId)
                    .ifPresent(reservation -> reservationTodayWalHandlerUseCase
                            .registerReservationTodayWal(userId, reservation.getMessage(), reservation.getSendDueDate()));
        }
    }
}
