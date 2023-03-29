package backend.wal.onboard.onboarding.domain.repository;

import backend.wal.onboard.onboarding.domain.aggregate.Onboarding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OnboardingRepository extends JpaRepository<Onboarding, Long> {
    Optional<Onboarding> findByUserId(Long userId);
}
