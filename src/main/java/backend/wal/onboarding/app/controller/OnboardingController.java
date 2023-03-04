package backend.wal.onboarding.app.controller;

import backend.wal.onboarding.app.dto.response.OnboardInfoResponse;
import backend.wal.onboarding.app.service.OnboardingService;
import backend.wal.onboarding.app.dto.request.InitOnboardInfoRequest;
import backend.wal.onboarding.app.dto.request.ModifyOnboardCategoryRequest;
import backend.wal.onboarding.app.dto.request.ModifyOnboardTimeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class OnboardingController {

    private final OnboardingService onboardingService;

    @PostMapping("/v2/onboard")
    public ResponseEntity<OnboardInfoResponse> setOnboardInfo(@Valid @RequestBody InitOnboardInfoRequest request,
                                                              Long userId) {
        return ResponseEntity.created(URI.create("/home"))
                .body(onboardingService.setOnboardInfo(request.toServiceDto(), userId));
    }

    @PostMapping("/v2/onboard/time/edit")
    public ResponseEntity<Void> updateOnboardTimeInfo(@Valid @RequestBody ModifyOnboardTimeRequest request,
                                                      Long userId) {
        onboardingService.updateOnboardTimeInfo(request.toServiceDto(), userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/v2/onboard/category/edit")
    public ResponseEntity<Void> updateOnboardCategoryInfo(@Valid @RequestBody ModifyOnboardCategoryRequest request,
                                                          Long userId) {
        onboardingService.updateOnboardCategoryInfo(request.toServiceDto(), userId);
        return ResponseEntity.noContent().build();
    }
}
