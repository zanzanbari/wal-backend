package backend.wal.home.web;

import backend.wal.home.application.port.RetrieveHomeUseCase;
import backend.wal.home.web.dto.HomeResponse;
import backend.wal.wal.todaywal.application.port.UpdateTodayWalShowStatusUseCase;
import backend.wal.support.annotation.Authentication;
import backend.wal.support.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/home")
public class HomeController {

    private final RetrieveHomeUseCase retrieveHomeUseCase;
    private final UpdateTodayWalShowStatusUseCase todayWalShowStatusUseCase;

    @Authentication
    @GetMapping
    public ResponseEntity<List<HomeResponse>> retrieveHome(@LoginUser Long userId) {
        return ResponseEntity.ok(retrieveHomeUseCase.getMainPage(userId));
    }

    @Authentication
    @PatchMapping("/{todayWalId}")
    public ResponseEntity<Void> updateShowStatus(@PathVariable Long todayWalId, @LoginUser Long userId) {
        todayWalShowStatusUseCase.updateShowStatus(todayWalId, userId);
        return ResponseEntity.noContent().build();
    }
}
