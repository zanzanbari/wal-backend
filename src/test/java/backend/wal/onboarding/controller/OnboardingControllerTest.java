package backend.wal.onboarding.controller;

import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.onboarding.domain.entity.WalTimeType;
import backend.wal.onboarding.dto.request.InitOnboardInfoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static backend.wal.onboarding.domain.entity.WalCategoryType.*;
import static backend.wal.onboarding.domain.entity.WalTimeType.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(OnboardingController.class)
class OnboardingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("")
    @Test
    void setOnboardInfo() throws Exception {
        // given
        String nickname = "COZO";
        Set<WalCategoryType> walCategoryTypes = Set.of(COMEDY, FUSS, COMFORT, YELL);
        Set<WalTimeType> walTimeTypes = Set.of(MORNING, AFTERNOON, NIGHT);
        InitOnboardInfoRequest request = new InitOnboardInfoRequest(nickname, walCategoryTypes, walTimeTypes);

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/v2/onboard")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }
}