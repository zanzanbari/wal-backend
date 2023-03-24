package backend.wal.home.controller;

import backend.wal.auth.support.Authentication;
import backend.wal.auth.support.LoginUser;
import backend.wal.home.app.service.HomeService;
import backend.wal.home.controller.dto.HomeResponse;
import backend.wal.wal.service.TodayWalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/home")
public class HomeController {

    private final HomeService homeService;
    private final TodayWalService todayWalService;

    @Authentication
    @GetMapping
    public ResponseEntity<List<HomeResponse>> retrieveHome(@LoginUser Long userId) {
        return ResponseEntity.ok(homeService.getMainPage(userId));
    }

    @Authentication
    @PatchMapping("/{todayWalId}")
    public ResponseEntity<Void> updateShowStatus(@PathVariable Long todayWalId, @LoginUser Long userId) {
        todayWalService.updateShowStatus(todayWalId, userId);
        return ResponseEntity.noContent().build();
    }
}
