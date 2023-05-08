package backend.wal.wal.onboarding.application.port.in;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;

import java.util.Set;

public interface RetrieveOnboardingInfoUseCase {

    Set<WalTimeType> retrieveTimeTypes(Long userId);

    Set<WalCategoryType> retrieveCategoryTypes(Long userId);
}
