package backend.wal.wal.todaywal.web;

import backend.wal.wal.todaywal.application.port.RetrieveHomeTodayWalUseCase;
import backend.wal.wal.todaywal.application.port.UpdateTodayWalShowStatusUseCase;
import backend.wal.wal.todaywal.application.port.dto.HomeResponseDto;
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
    private final UpdateTodayWalShowStatusUseCase updateTodayWalShowStatusUseCase;

    public TodayWalHomeController(final RetrieveHomeTodayWalUseCase retrieveHomeTodayWalUseCase,
                                  final UpdateTodayWalShowStatusUseCase updateTodayWalShowStatusUseCase) {
        this.retrieveHomeTodayWalUseCase = retrieveHomeTodayWalUseCase;
        this.updateTodayWalShowStatusUseCase = updateTodayWalShowStatusUseCase;
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
        updateTodayWalShowStatusUseCase.updateShowStatus(todayWalId, userId);
        return ResponseEntity.noContent().build();
    }
}
