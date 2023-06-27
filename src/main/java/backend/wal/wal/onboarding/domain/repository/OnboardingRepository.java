package backend.wal.wal.onboarding.domain.repository;

import backend.wal.wal.onboarding.domain.aggregate.Onboarding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OnboardingRepository extends JpaRepository<Onboarding, Long> {

    Optional<Onboarding> findByUserId(Long userId);

    @Query("SELECT DISTINCT o FROM Onboarding o JOIN FETCH o.times")
    List<Onboarding> findOnboardingsWithTimeTypes();

    void deleteByUserId(Long userId);
}
