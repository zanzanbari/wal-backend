package backend.wal.wal.onboarding.web.dto;

import backend.wal.wal.onboarding.application.port.in.dto.InitOnboardInfoRequestDto;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InitOnboardInfoRequest {

    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;

    @NotNull(message = "원하는 카테고리를 선택하세요")
    private Set<WalCategoryType> categoryTypes;

    @NotNull(message = "원하는 시간대를 선택하세요")
    private Set<WalTimeType> timeTypes;

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
