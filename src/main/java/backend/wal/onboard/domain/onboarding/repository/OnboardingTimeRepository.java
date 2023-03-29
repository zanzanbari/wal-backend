package backend.wal.onboard.domain.onboarding.repository;

import backend.wal.onboard.domain.common.WalTimeType;
import backend.wal.onboard.domain.onboarding.aggregate.OnboardingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OnboardingTimeRepository extends JpaRepository<OnboardingTime, Long> {

    @Query("SELECT ot.onboarding.userId FROM OnboardingTime ot WHERE ot.timeType = :walTimeType")
    List<Long> findUserIdsByTimeType(WalTimeType walTimeType);
}
