package backend.wal.onboarding.app.dto.request;

import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.onboarding.domain.entity.WalTimeType;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
public final class InitOnboardInfoRequest {

    @NotBlank(message = "닉네임을 입력해주세요")
    private final String nickname;

    @NotNull(message = "원하는 카테고리를 선택하세요")
    private final Set<WalCategoryType> categoryTypes;

    @NotNull(message = "원하는 시간대를 선택하세요")
    private final Set<WalTimeType> timeTypes;

    public InitOnboardInfoRequest(final String nickname, final Set<WalCategoryType> categoryTypes,
                                  final Set<WalTimeType> timeTypes) {
        this.nickname = nickname;
        this.categoryTypes = categoryTypes;
        this.timeTypes = timeTypes;
    }

    public InitOnboardInfoRequestDto toServiceDto() {
        return new InitOnboardInfoRequestDto(nickname, categoryTypes, timeTypes);
    }
}
