package com.kpay.point.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("적립금 계산 테스트")
public class CalculatePointTests {
    private final AccumulateMemberShipUsecase usecase =
            new AccumulateMemberShipUsecase(null, null);

    @Test
    @DisplayName("0원, 음수일때도 적립금액은 0원")
    void test1() {
        assertAll(
                () -> assertThat(usecase.calculatePoint(0)).isEqualTo(0),
                () -> assertThat(usecase.calculatePoint(-100)).isEqualTo(0));
    }

    @Test
    @DisplayName("소수점이 딱 떨어지지 않으면 무조건 올림 처리")
    void test2() {
        assertAll(
                () -> assertThat(usecase.calculatePoint(110)).isEqualTo(2),
                () -> assertThat(usecase.calculatePoint(253)).isEqualTo(3),
                () -> assertThat(usecase.calculatePoint(5230)).isEqualTo(53));
    }
}
