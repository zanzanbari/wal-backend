package backend.wal.onboard.onboarding.web;

import backend.wal.onboard.onboarding.application.port.RegisterOnboardingUseCase;
import backend.wal.onboard.onboarding.application.port.UpdateOnboardingCategoryUseCase;
import backend.wal.onboard.onboarding.application.port.UpdateOnboardingTimeUseCase;
import backend.wal.onboard.onboarding.web.dto.InitOnboardInfoRequest;
import backend.wal.onboard.onboarding.web.dto.OnboardInfoResponse;
import backend.wal.onboard.onboarding.web.dto.ModifyOnboardCategoryRequest;
import backend.wal.onboard.onboarding.web.dto.ModifyOnboardTimeRequest;
import backend.wal.support.annotation.Authentication;
import backend.wal.support.annotation.LoginUser;
import backend.wal.user.application.port.ChangeUserInfoUseCase;
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

    private final ChangeUserInfoUseCase changeUserInfoUseCase;
    private final RegisterOnboardingUseCase registerOnboardingUseCase;
    private final UpdateOnboardingTimeUseCase updateOnboardingTimeUseCase;
    private final UpdateOnboardingCategoryUseCase updateOnboardingCategoryUseCase;

    @Authentication
    @PostMapping
    public ResponseEntity<OnboardInfoResponse> setOnboardInfo(@Valid @RequestBody InitOnboardInfoRequest request,
                                                              @LoginUser Long userId) {
        registerOnboardingUseCase.registerOnboardInfo(request.toServiceDto(), userId);
        return ResponseEntity.created(URI.create("/v2/home"))
                .body(new OnboardInfoResponse(changeUserInfoUseCase.changeNickname(request.getNickname(), userId)));
    }

    @PostMapping("/time/edit")
    public ResponseEntity<Void> updateOnboardTimeInfo(@Valid @RequestBody ModifyOnboardTimeRequest request,
                                                      @LoginUser Long userId) {
        updateOnboardingTimeUseCase.updateOnboardTimeInfo(request.toServiceDto(), userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/category/edit")
    public ResponseEntity<Void> updateOnboardCategoryInfo(@Valid @RequestBody ModifyOnboardCategoryRequest request,
                                                          @LoginUser Long userId) {
        updateOnboardingCategoryUseCase.updateOnboardCategoryInfo(request.toServiceDto(), userId);
        return ResponseEntity.noContent().build();
    }
}