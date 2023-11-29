package backend.wal.wal.onboarding.domain.repository;

import backend.wal.wal.common.domain.WalTimeType;

import java.util.List;

@Deprecated(forRemoval = true)
public interface JdbcOnboardingTimeRepository {

    void saveAllInBatch(List<WalTimeType> timeTypes, Long onboardId);
}
