package backend.wal.wal.onboarding.web.dto;

public class OnboardInfoResponse {

    private final String nickname;

    public OnboardInfoResponse(final String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
