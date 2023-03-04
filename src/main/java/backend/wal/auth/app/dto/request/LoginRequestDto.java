package backend.wal.auth.app.dto.request;

import backend.wal.user.domain.entity.SocialType;
import backend.wal.user.app.dto.request.CreateUserDto;
import lombok.Getter;

@Getter
public final class LoginRequestDto {

    private final String socialAccessToken;
    private final SocialType socialType;
    private final String fcmToken;

    public LoginRequestDto(final String socialAccessToken, final SocialType socialType, final String fcmToken) {
        this.socialAccessToken = socialAccessToken;
        this.socialType = socialType;
        this.fcmToken = fcmToken;
    }

    public CreateUserDto toCreateUserDto(String nickname, String socialId) {
        return CreateUserDto.builder()
                .nickname(nickname)
                .socialId(socialId)
                .socialType(socialType)
                .build();
    }

    public InitFcmRequestDto toFcmTokenServiceDto(Long userId) {
        return new InitFcmRequestDto(userId, fcmToken);
    }
}
