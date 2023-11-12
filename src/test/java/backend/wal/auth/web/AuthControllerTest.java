package backend.wal.auth.web;

import backend.wal.auth.application.port.in.AuthUseCase;
import backend.wal.auth.application.port.in.IssueTokenUseCase;
import backend.wal.auth.application.port.in.LoginResponseDto;
import backend.wal.auth.application.port.in.TokenResponseDto;
import backend.wal.auth.application.provider.AuthServiceProvider;
import backend.wal.auth.web.dto.LoginRequest;
import backend.wal.support.Role;
import backend.wal.support.utils.HttpHeaderUtils;
import backend.wal.user.domain.aggregate.SocialType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private static final String USER_ROLE = Role.USER.name();

    private MockMvc mockMvc;

    @Mock
    private AuthServiceProvider authServiceProvider;

    @Mock
    private IssueTokenUseCase issueTokenUseCase;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .build();
    }

    @DisplayName("social token, social type, fcm token 값을 받아 요청 유저를 회원가입 처리한다")
    @Test
    void signup() throws Exception {
        // given
        String socialToken = "social-token";
        String fcmToken = "fcm-token";
        LoginRequest loginRequest = new LoginRequest(socialToken, SocialType.KAKAO, fcmToken);
        AuthUseCase authUseCase = mock(AuthUseCase.class);
        when(authServiceProvider.getAuthServiceBy(loginRequest.getSocialType()))
                .thenReturn(authUseCase);

        Long userId = 1L;
        LoginResponseDto loginResponseDto = new LoginResponseDto(userId, "nickname", USER_ROLE, true);
        when(authUseCase.login(any()))
                .thenReturn(loginResponseDto);

        String accessToken = "access-token";
        String refreshToken = "refresh-token";
        TokenResponseDto tokenResponseDto = new TokenResponseDto(accessToken, refreshToken);
        when(issueTokenUseCase.issueForNewUser(userId, USER_ROLE))
                .thenReturn(tokenResponseDto);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/v2/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaderUtils.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(header().string(HttpHeaderUtils.REFRESH_TOKEN, refreshToken));
    }

    @DisplayName("social token, social type, fcm token 값을 받아 요청 유저를 로그인 처리한다")
    @Test
    void login() throws Exception {
        // given
        String socialToken = "social-token";
        String fcmToken = "fcm-token";
        LoginRequest loginRequest = new LoginRequest(socialToken, SocialType.KAKAO, fcmToken);
        AuthUseCase authUseCase = mock(AuthUseCase.class);
        when(authServiceProvider.getAuthServiceBy(loginRequest.getSocialType()))
                .thenReturn(authUseCase);

        Long userId = 1L;
        LoginResponseDto loginResponseDto = new LoginResponseDto(userId, "nickname", USER_ROLE, false);
        when(authUseCase.login(any()))
                .thenReturn(loginResponseDto);

        String accessToken = "access-token";
        String refreshToken = "refresh-token";
        TokenResponseDto tokenResponseDto = new TokenResponseDto(accessToken, refreshToken);
        when(issueTokenUseCase.issueForAlreadyUser(userId, USER_ROLE))
                .thenReturn(tokenResponseDto);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/v2/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaderUtils.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(header().string(HttpHeaderUtils.REFRESH_TOKEN, refreshToken));
    }

    @DisplayName("유효한 refresh token 값을 받아 access token 을 재발급한다")
    @Test
    void reissue() throws Exception {
        // given
        String refreshToken = "valid-refresh-token";
        String reissuedAccessToken = "reissued-access-token";
        when(issueTokenUseCase.reissue(any(), any()))
                .thenReturn(reissuedAccessToken);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/v2/auth/reissue")
                        .header(HttpHeaderUtils.REFRESH_TOKEN, refreshToken))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaderUtils.AUTHORIZATION, "Bearer " + reissuedAccessToken));
    }
}
