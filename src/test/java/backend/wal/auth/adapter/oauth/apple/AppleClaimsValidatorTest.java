package backend.wal.auth.adapter.oauth.apple;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AppleClaimsValidatorTest {

    private static final String ISS = "WAL_ISSUER";
    private static final String CLIENT_ID = "WAL_CLIENT_ID";
    private static final long EXPIRED_TIME = 1000;

    private final AppleClaimsValidator appleClaimsValidator = new AppleClaimsValidator(ISS, CLIENT_ID);

    @DisplayName("issuer 와 clientId 가 모두 같으면 true, 하나라도 다르다면 false 를 반환한다")
    @ParameterizedTest
    @MethodSource("provideIssuerAndClientIdAndExpect")
    void hasRightIssAndClientId(String issuer, String clientId, boolean expect) {
        // when
        boolean actual = appleClaimsValidator.hasRightIssAndClientId(issuer, clientId);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideIssuerAndClientIdAndExpect() {
        return Stream.of(
                Arguments.of(ISS, CLIENT_ID, true),
                Arguments.of("WRONG_ISS", CLIENT_ID, false),
                Arguments.of(ISS, "WRONG_CLIENT_ID", false),
                Arguments.of("WRONG_ISS", "WRONG_CLIENT_ID", false)
        );
    }

    @DisplayName("만료시간과 현재 시간을 비교해, 만료시간이 현재 시간보다 크면 true 를, 작으면 false 를 반환한다")
    @ParameterizedTest
    @MethodSource("provideExpiredTimeAndCurrentTimeAndExpect")
    void isValidExp(long expiration, long now, boolean expect) {
        // when
        boolean actual = appleClaimsValidator.isValidExp(expiration, now);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provideExpiredTimeAndCurrentTimeAndExpect() {
        return Stream.of(
                Arguments.of(EXPIRED_TIME, EXPIRED_TIME - 1, true),
                Arguments.of(EXPIRED_TIME, EXPIRED_TIME + 1, false),
                Arguments.of(EXPIRED_TIME, EXPIRED_TIME, false)
        );
    }
}