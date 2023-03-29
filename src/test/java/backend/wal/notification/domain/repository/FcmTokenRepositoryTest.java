package backend.wal.notification.domain.repository;

import backend.wal.config.JpaRepositoryTestConfig;
import backend.wal.notification.domain.FcmToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@JpaRepositoryTestConfig
class FcmTokenRepositoryTest {

    private static final Long USER_ID = 1L;
    private static final Long NOT_EXIST_USER_ID = 2L;

    @Autowired
    private FcmTokenRepository fcmTokenRepository;

    private FcmToken fcmToken;

    @BeforeEach
    void setUp() {
        fcmToken = FcmToken.newInstance(USER_ID, "FCM_TOKEN_VALUE");
        fcmTokenRepository.save(fcmToken);
    }

    @DisplayName("")
    @ParameterizedTest
    @MethodSource("provide")
    void existsFcmTokenByUserId(Long userId, boolean expect) {
        // when
        boolean actual = fcmTokenRepository.existsFcmTokenByUserId(userId);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> provide() {
        return Stream.of(
                Arguments.of(USER_ID, true),
                Arguments.of(NOT_EXIST_USER_ID, false)
        );
    }

    @DisplayName("")
    @Test
    void findFcmTokenByUserId() {
        // when
        FcmToken find = fcmTokenRepository.findFcmTokenByUserId(USER_ID);

        // then
        assertThat(find).isEqualTo(fcmToken);
    }
}