package backend.wal.auth.application.port.in;

import backend.wal.notification.application.port.in.InitFcmRequestDto;
import backend.wal.user.domain.aggregate.SocialType;
import backend.wal.user.application.port.in.CreateUserDto;
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
