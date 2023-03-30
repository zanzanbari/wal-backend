package backend.wal.wal.todaywal.web;

import backend.wal.wal.todaywal.application.port.in.RetrieveHomeTodayWalUseCase;
import backend.wal.wal.todaywal.application.port.in.UpdateTodayWalUseCase;
import backend.wal.wal.todaywal.application.port.in.HomeResponseDto;
import backend.wal.wal.todaywal.web.dto.HomeResponse;
import backend.wal.support.annotation.Authentication;
import backend.wal.support.annotation.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v2/today-wal")
public class TodayWalHomeController {

    private final RetrieveHomeTodayWalUseCase retrieveHomeTodayWalUseCase;
    private final UpdateTodayWalUseCase updateTodayWalUseCase;

    public TodayWalHomeController(final RetrieveHomeTodayWalUseCase retrieveHomeTodayWalUseCase,
                                  final UpdateTodayWalUseCase updateTodayWalUseCase) {
        this.retrieveHomeTodayWalUseCase = retrieveHomeTodayWalUseCase;
        this.updateTodayWalUseCase = updateTodayWalUseCase;
    }

    @Authentication
    @GetMapping
    public ResponseEntity<List<HomeResponse>> retrieveHome(@LoginUser Long userId) {
        List<HomeResponse> homeResponses = retrieveHomeTodayWalUseCase.getHomeInfo(userId)
                .stream()
                .map(HomeResponseDto::toWebResponse)
                .collect(Collectors.toUnmodifiableList());
        return ResponseEntity.ok(homeResponses);
    }

    @Authentication
    @PatchMapping("/{todayWalId}")
    public ResponseEntity<Void> updateShowStatus(@PathVariable Long todayWalId, @LoginUser Long userId) {
        updateTodayWalUseCase.updateShowStatus(todayWalId, userId);
        return ResponseEntity.noContent().build();
    }
}
