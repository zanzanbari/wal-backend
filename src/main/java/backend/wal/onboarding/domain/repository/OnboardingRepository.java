package backend.wal.onboarding.domain.repository;

import backend.wal.onboarding.domain.entity.Onboarding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardingRepository extends JpaRepository<Onboarding, Long> {
    Onboarding findByUserId(Long userId);
}
