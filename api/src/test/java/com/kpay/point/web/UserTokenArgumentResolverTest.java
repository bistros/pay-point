package com.kpay.point.web;

import com.kpay.point.shared.domain.User;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

class UserTokenArgumentResolverTest {

    private MethodParameter parameter;
    private NativeWebRequest request;
    private UserTokenArgumentResolver argumentResolver;

    @BeforeEach
    public void setup() {
        request = mock(NativeWebRequest.class);
        argumentResolver = new UserTokenArgumentResolver();
    }

    @Test
    @DisplayName("헤더에서 사용자 정보를 가져온다")
    public void test1() throws Exception {
        var userId = "xxxx";
        when(request.getHeader(UserTokenArgumentResolver.USER_HEADER_NAME)).thenReturn(userId);
        User user = (User) argumentResolver.resolveArgument(null, null, request, null);
        assertThat(user.getId()).isEqualTo(userId);
    }

}