package backend.wal.user.application.port.in;

import backend.wal.user.application.port.out.CategoryTypeResponseDto;
import backend.wal.user.application.port.out.TimeTypeResponseDto;

public interface RetrieveUserInfoUseCase {

    String retrieveNickname(Long userId);

    TimeTypeResponseDto retrieveTimeInfo(Long userId);

    CategoryTypeResponseDto retrieveCategoryInfo(Long userId);
}
