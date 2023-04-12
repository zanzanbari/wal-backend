package backend.wal.wal.todaywal.application.port.in;

import java.util.List;

public interface RetrieveHomeTodayWalUseCase {

    List<HomeResponseDto> getHomeInfo(Long userId);
}
