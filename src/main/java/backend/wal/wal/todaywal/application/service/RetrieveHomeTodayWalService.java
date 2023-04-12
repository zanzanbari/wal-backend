package backend.wal.wal.todaywal.application.service;

import backend.wal.wal.todaywal.application.port.in.HomeResponseDto;
import backend.wal.wal.todaywal.application.port.in.RetrieveHomeTodayWalUseCase;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
import backend.wal.wal.todaywal.domain.view.Home;
import backend.wal.wal.todaywal.domain.view.Homes;
import backend.wal.support.annotation.AppService;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AppService
public class RetrieveHomeTodayWalService implements RetrieveHomeTodayWalUseCase {

    private final TodayWalRepository todayWalRepository;
    private final Clock clock;

    public RetrieveHomeTodayWalService(final TodayWalRepository todayWalRepository, final Clock clock) {
        this.todayWalRepository = todayWalRepository;
        this.clock = clock;
    }

    @Override
    public List<HomeResponseDto> getHomeInfo(Long userId) {
        List<Home> homeInfo = todayWalRepository.findTodayWalsByUserId(userId)
                .stream()
                .map(Home::of)
                .collect(Collectors.toList());

        Homes homes = Homes.create(homeInfo, LocalDateTime.now(clock));
        return homes.getServiceResponseDto();
    }
}
