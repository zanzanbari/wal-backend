package backend.wal.auth.web.dto;

import backend.wal.auth.application.port.in.LoginRequestDto;
import backend.wal.user.domain.aggregate.SocialType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LoginRequest {

    @NotBlank(message = "apple idToken 또는 kakao accessToken 이 필요합니다")
    private String socialToken;

    @NotNull(message = "소셜 로그인할 타입을 입력하세요")
    private SocialType socialType;

    @NotBlank(message = "fcmToken 은 필수 입력값입니다")
    private String fcmToken;

    private LoginRequest() {
    }

    public LoginRequest(final String socialToken, final SocialType socialType, final String fcmToken) {
        this.socialToken = socialToken;
        this.socialType = socialType;
        this.fcmToken = fcmToken;
    }

    public LoginRequestDto toAuthServiceDto() {
        return new LoginRequestDto(socialToken, socialType, fcmToken);
    }

    public String getSocialToken() {
        return socialToken;
    }

    public SocialType getSocialType() {
        return socialType;
    }

    public String getFcmToken() {
        return fcmToken;
    }
}
