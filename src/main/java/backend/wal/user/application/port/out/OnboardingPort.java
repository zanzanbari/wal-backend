package backend.wal.user.application.port.out;

public interface OnboardingPort {

    TimeTypeResponseDto retrieveTimeTypes(Long userId);

    CategoryTypeResponseDto retrieveCategoryTypes(Long userId);
}
