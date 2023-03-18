package backend.wal.home.app.service;

import backend.wal.home.app.dto.HomeResponseDto;
import backend.wal.home.controller.dto.HomeResponse;
import backend.wal.home.domain.Homes;
import backend.wal.home.domain.entity.Home;
import backend.wal.wal.domain.repository.TodayWalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final TodayWalRepository todayWalRepository;
    private final Clock clock;

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
