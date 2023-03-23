package backend.wal.user.domain.aggregate.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum SocialType {

    APPLE("애플"),
    KAKAO("카카오"),
    ;

    private final String value;
}
