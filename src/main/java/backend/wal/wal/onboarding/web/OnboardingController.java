package backend.wal.wal.onboarding.web;

import backend.wal.wal.onboarding.application.port.in.RegisterOnboardingUseCase;
import backend.wal.wal.onboarding.application.port.in.UpdateOnboardingCategoryUseCase;
import backend.wal.wal.onboarding.application.port.in.UpdateOnboardingTimeUseCase;
import backend.wal.wal.onboarding.web.dto.InitOnboardInfoRequest;
import backend.wal.wal.onboarding.web.dto.OnboardInfoResponse;
import backend.wal.wal.onboarding.web.dto.ModifyOnboardCategoryRequest;
import backend.wal.wal.onboarding.web.dto.ModifyOnboardTimeRequest;
import backend.wal.support.annotation.Authentication;
import backend.wal.support.annotation.LoginUser;
import backend.wal.user.application.port.in.ChangeUserInfoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v2/onboard")
public class OnboardingController {

    private final ChangeUserInfoUseCase changeUserInfoUseCase;
    private final RegisterOnboardingUseCase registerOnboardingUseCase;
    private final UpdateOnboardingTimeUseCase updateOnboardingTimeUseCase;
    private final UpdateOnboardingCategoryUseCase updateOnboardingCategoryUseCase;

    public OnboardingController(final ChangeUserInfoUseCase changeUserInfoUseCase,
                                final RegisterOnboardingUseCase registerOnboardingUseCase,
                                final UpdateOnboardingTimeUseCase updateOnboardingTimeUseCase,
                                final UpdateOnboardingCategoryUseCase updateOnboardingCategoryUseCase) {
        this.changeUserInfoUseCase = changeUserInfoUseCase;
        this.registerOnboardingUseCase = registerOnboardingUseCase;
        this.updateOnboardingTimeUseCase = updateOnboardingTimeUseCase;
        this.updateOnboardingCategoryUseCase = updateOnboardingCategoryUseCase;
    }

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
