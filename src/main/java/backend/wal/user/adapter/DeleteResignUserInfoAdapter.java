package backend.wal.user.adapter;

import backend.wal.auth.domain.repository.RefreshTokenRepository;
import backend.wal.notification.domain.repository.FcmTokenRepository;
import backend.wal.reservation.domain.repository.ReservationRepository;
import backend.wal.reservation.domain.repository.ScheduledMessageRepository;
import backend.wal.user.application.port.out.DeleteResignUserInfoPort;
import backend.wal.wal.nextwal.domain.repository.NextWalRepository;
import backend.wal.wal.onboarding.domain.repository.OnboardingRepository;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;

import org.springframework.stereotype.Component;

@Component
public class DeleteResignUserInfoAdapter implements DeleteResignUserInfoPort {

    private final FcmTokenRepository fcmTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ReservationRepository reservationRepository;
    private final ScheduledMessageRepository scheduledMessageRepository;
    private final OnboardingRepository onboardingRepository;
    private final NextWalRepository nextWalRepository;
    private final TodayWalRepository todayWalRepository;

    public DeleteResignUserInfoAdapter(final FcmTokenRepository fcmTokenRepository,
                                       final RefreshTokenRepository refreshTokenRepository,
                                       final ReservationRepository reservationRepository,
                                       final ScheduledMessageRepository scheduledMessageRepository,
                                       final OnboardingRepository onboardingRepository,
                                       final NextWalRepository nextWalRepository,
                                       final TodayWalRepository todayWalRepository) {
        this.fcmTokenRepository = fcmTokenRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.reservationRepository = reservationRepository;
        this.scheduledMessageRepository = scheduledMessageRepository;
        this.onboardingRepository = onboardingRepository;
        this.nextWalRepository = nextWalRepository;
        this.todayWalRepository = todayWalRepository;
    }

    @Override
    public void deleteAll(Long userId) {
        fcmTokenRepository.deleteByUserId(userId);
        refreshTokenRepository.deleteAllByUserId(userId);
        reservationRepository.deleteAllByUserId(userId);
        scheduledMessageRepository.deleteAllByUserId(userId);
        onboardingRepository.deleteByUserId(userId);
        nextWalRepository.deleteAllByUserId(userId);
        todayWalRepository.deleteAllByUserId(userId);
    }
}
