package backend.wal.onboarding.domain.repository;

import backend.wal.onboarding.domain.entity.OnboardingTime;
import backend.wal.onboarding.domain.entity.WalTimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OnboardingTimeRepository extends JpaRepository<OnboardingTime, Long> {

    @Query("SELECT ot.onboarding.userId FROM OnboardingTime ot WHERE ot.walTimeType = :walTimeType")
    List<Long> findUserIdsByTimeType(WalTimeType walTimeType);
}
