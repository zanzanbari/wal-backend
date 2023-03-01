package backend.wal.user.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public final class ModifyNicknameRequest {

    @NotBlank(message = "새로운 닉네임을 입력하세요")
    private final String nickname;

    public ModifyNicknameRequest(final String nickname) {
        this.nickname = nickname;
    }
}
