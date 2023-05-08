package backend.wal.user.web.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyNicknameRequest {

    @NotBlank(message = "새로운 닉네임을 입력하세요")
    private String nickname;

    public ModifyNicknameRequest(final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
