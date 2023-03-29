package backend.wal.onboard.domain.onboarding.repository;

import backend.wal.onboard.domain.onboarding.aggregate.Onboarding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OnboardingRepository extends JpaRepository<Onboarding, Long> {
    Optional<Onboarding> findByUserId(Long userId);
}
