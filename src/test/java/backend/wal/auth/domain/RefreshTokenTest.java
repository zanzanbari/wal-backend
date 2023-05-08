package backend.wal.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenTest {

    @DisplayName("리프레시 토큰 값과 토큰 만료 시간을 업데이트 하면 토큰 값과 만료 시간이 변경된다")
    @Test
    void updateRefreshTokenValueAndExpiredAt() {
        // given
        Date nowDate = new Date();
        Date updateDate = new Date(nowDate.getTime() + 1000);
        RefreshToken refreshToken = RefreshToken.newInstance(1L, "refreshToken", nowDate);

        // when
        refreshToken.updateRefreshTokenValueAndExpiredAt("newRefreshToken", updateDate);

        // then
        assertThat(refreshToken.getValue()).isEqualTo("newRefreshToken");
    }
}