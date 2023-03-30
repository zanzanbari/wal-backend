package backend.wal.wal.todaywal.application.port;

import backend.wal.wal.todaywal.application.port.dto.HomeResponseDto;

import java.util.List;

public interface RetrieveHomeTodayWalUseCase {

    List<HomeResponseDto> getHomeInfo(Long userId);
}
