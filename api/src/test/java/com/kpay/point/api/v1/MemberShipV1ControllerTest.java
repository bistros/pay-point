package com.kpay.point.api.v1;

import com.kpay.point.api.ApiExceptionHandler;
import com.kpay.point.shared.domain.MemberShip;
import com.kpay.point.shared.domain.MemberShipType;
import com.kpay.point.usecases.AllCheckMemberShipUsecase;
import com.kpay.point.usecases.CheckMemberShipUsecase;
import com.kpay.point.web.UserTokenArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

@Tag("SpringTest")
@WebMvcTest(MemberShipV1Controller.class)
class MemberShipV1ControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    AllCheckMemberShipUsecase allCheckMemberShipUsecase;
    @MockBean
    CheckMemberShipUsecase checkMemberShipUsecase;


    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MemberShipV1Controller(allCheckMemberShipUsecase, checkMemberShipUsecase))
                .setCustomArgumentResolvers(new UserTokenArgumentResolver())
                .setControllerAdvice(new ApiExceptionHandler())
                .alwaysExpect(MockMvcResultMatchers.status().isOk())
                .build();
    }

    @Test
    @DisplayName("멤버쉽을 조회한다")
    public void test2() throws Exception {
        var userId = "12345";
        var memberShip = MemberShip.builder()
                .seq(1L)
                .startDate(LocalDateTime.now())
                .userId(userId)
                .memberShipType(MemberShipType.CJONE)
                .totalPoint(2000)
                .active(true)
                .build();
        var response = new CheckMemberShipUsecase.CheckMemberShipResponse(memberShip);
        when(checkMemberShipUsecase.apply(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/membership/cj")
                .header("X-USER-ID", userId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.response.userId").value(userId))
                .andExpect(jsonPath("$.response.point").value(2000));
    }


}