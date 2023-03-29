package backend.wal.home.application.service;

import backend.wal.home.application.port.RetrieveHomeUseCase;
import backend.wal.home.application.port.dto.HomeResponseDto;
import backend.wal.home.web.dto.HomeResponse;
import backend.wal.home.domain.Homes;
import backend.wal.home.domain.Home;
import backend.wal.onboard.domain.todaywal.repository.TodayWalRepository;
import backend.wal.support.annotation.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AppService
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RetrieveHomeService implements RetrieveHomeUseCase {

    private final TodayWalRepository todayWalRepository;
    private final Clock clock;

    @Override
    public List<HomeResponse> getMainPage(Long userId) {
        List<Home> homeInfo = todayWalRepository.findTodayWalsByUserId(userId)
                .stream()
                .map(todayWal -> new Home(
                        todayWal.getId(),
                        todayWal.getTimeType(),
                        todayWal.getCategoryType(),
                        todayWal.getMessage(),
                        todayWal.getShowStatus()))
                .collect(Collectors.toList());

        Homes homes = Homes.create(homeInfo, LocalDateTime.now(clock));
        List<HomeResponseDto> homeResponseDtos = homes.getServiceResponseDto();

        return homeResponseDtos.stream()
                .map(HomeResponseDto::toWebResponse)
                .collect(Collectors.toList());
    }
}
