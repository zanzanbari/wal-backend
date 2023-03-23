package backend.wal.user.app.dto.request;

import backend.wal.user.domain.aggregate.vo.SocialType;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class CreateUserDto {
    
    private final String nickname;
    private final String socialId;
    private final SocialType socialType;

    @Builder
    private CreateUserDto(final String nickname, final String socialId, final SocialType socialType) {
        this.nickname = nickname;
        this.socialId = socialId;
        this.socialType = socialType;
    }
}
