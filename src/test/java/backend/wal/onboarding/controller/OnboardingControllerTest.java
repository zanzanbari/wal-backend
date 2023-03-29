package backend.wal.onboarding.controller;

import backend.wal.wal.onboarding.web.OnboardingController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OnboardingController.class)
class OnboardingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @DisplayName("")
//    @Test
//    void setOnboardInfo() throws Exception {
//        // given
//        String nickname = "COZO";
//        Set<WalCategoryType> walCategoryTypes = Set.of(COMEDY, FUSS, COMFORT, YELL);
//        Set<WalTimeType> walTimeTypes = Set.of(MORNING, AFTERNOON, NIGHT);
//        InitOnboardInfoRequest request = new InitOnboardInfoRequest(nickname, walCategoryTypes, walTimeTypes);
//
//        // when
//        ResultActions result = mockMvc.perform(
//                MockMvcRequestBuilders.post("/v2/onboard")
//                        .content(objectMapper.writeValueAsString(request))
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//
//        // then
//        result.andExpect(MockMvcResultMatchers.status().isCreated());
//    }
}