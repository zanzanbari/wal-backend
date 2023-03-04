package backend.wal.auth.app.dto.request;

import backend.wal.user.domain.entity.SocialType;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Getter
public final class LoginRequest {

    @NotBlank(message = "apple idToken 또는 kakao accessToken 이 필요합니다")
    private final String socialToken;
    @NotNull(message = "소셜 로그인할 타입을 입력하세요")
    private final SocialType socialType;
    @NotBlank(message = "fcmToken 은 필수 입력값입니다")
    private final String fcmToken;

    public LoginRequest(final String socialToken, final SocialType socialType, final String fcmToken) {
        this.socialToken = socialToken;
        this.socialType = socialType;
        this.fcmToken = fcmToken;
    }

    public LoginRequestDto toAuthServiceDto() {
        return new LoginRequestDto(socialToken, socialType, fcmToken);
    }
}
