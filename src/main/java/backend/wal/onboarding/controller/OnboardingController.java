package backend.wal.onboarding.controller;

import backend.wal.auth.support.Authentication;
import backend.wal.auth.support.LoginUser;
import backend.wal.onboarding.app.dto.response.OnboardInfoResponse;
import backend.wal.onboarding.app.service.OnboardingService;
import backend.wal.onboarding.controller.dto.InitOnboardInfoRequest;
import backend.wal.onboarding.controller.dto.ModifyOnboardCategoryRequest;
import backend.wal.onboarding.controller.dto.ModifyOnboardTimeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/onboard")
public class OnboardingController {

    private final OnboardingService onboardingService;

    @Authentication
    @PostMapping
    public ResponseEntity<OnboardInfoResponse> setOnboardInfo(@Valid @RequestBody InitOnboardInfoRequest request,
                                                              @LoginUser Long userId) {
        return ResponseEntity.created(URI.create("/v2/home"))
                .body(onboardingService.setOnboardInfo(request.toServiceDto(), userId));
    }

    @PostMapping("/time/edit")
    public ResponseEntity<Void> updateOnboardTimeInfo(@Valid @RequestBody ModifyOnboardTimeRequest request,
                                                      @LoginUser Long userId) {
        onboardingService.updateOnboardTimeInfo(request.toServiceDto(), userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/category/edit")
    public ResponseEntity<Void> updateOnboardCategoryInfo(@Valid @RequestBody ModifyOnboardCategoryRequest request,
                                                          @LoginUser Long userId) {
        onboardingService.updateOnboardCategoryInfo(request.toServiceDto(), userId);
        return ResponseEntity.noContent().build();
    }
}
