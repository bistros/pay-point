package com.kpay.point.usecases;

import com.kpay.point.infrastructure.persistence.MemberShipRepositoryJpa;
import com.kpay.point.shared.domain.MemberShipType;
import com.kpay.point.shared.domain.User;
import com.kpay.point.shared.exception.AlreadyDeactiveStateException;
import com.kpay.point.shared.exception.MemberShipNotFoundException;
import com.kpay.point.shared.model.Usecase;
import com.kpay.point.shared.model.VoidResponseUsecase;
import static com.kpay.point.shared.model.VoidResponseUsecase.VOID;
import com.kpay.point.usecases.DeactivateMemberShipUsecase.DeactivateMemberShipRequest;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

@DisplayName("멤버쉽 비활성화 테스트#1")
class DeactivateMemberShipUsecaseTests extends BaseTestDataMother {

    private Usecase<DeactivateMemberShipRequest, VoidResponseUsecase> usecase;

    @Nested
    @DisplayName("비활성화 시도를 할 때")
    class ExistDeactiveUserTests {
        private MemberShipRepositoryJpa memberShipRepository;

        @BeforeEach
        void init() {
            memberShipRepository = mock(MemberShipRepositoryJpa.class);
        }

        @Test
        @DisplayName("이미 DeActive된 경우 예외 발생")
        void test1() {
            var memberShip = DeActiveTestData.deActiveMemberShip();
            when(memberShipRepository
                    .findByUserIdAndMembershipId(memberShip.getUserId(), memberShip.getMembershipId()))
                    .thenReturn(Optional.of(memberShip));
            usecase = new DeactivateMemberShipUsecase(memberShipRepository);

            var request = new DeactivateMemberShipRequest(new User("111"), MemberShipType.CJONE);

            assertThrows(AlreadyDeactiveStateException.class, () -> usecase.apply(request));
        }

        @Test
        @DisplayName("정상 사용자는 DeActive로 변경됨")
        void test2() {
            var memberShip = DeActiveTestData.activeMemberShip();
            when(memberShipRepository
                    .findByUserIdAndMembershipId(memberShip.getUserId(), memberShip.getMembershipId()))
                    .thenReturn(Optional.of(memberShip));
            usecase = new DeactivateMemberShipUsecase(memberShipRepository);

            var request = new DeactivateMemberShipRequest(new User("111"), MemberShipType.CJONE);
            var response = usecase.apply(request);

            assertThat(response).isEqualTo(VOID);
        }

        @Test
        @DisplayName("사용자가 없으면 예외가 발생한다")
        void test3() {
            when(memberShipRepository
                    .findByUserIdAndMembershipId(anyString(), anyString()))
                    .thenReturn(Optional.empty());
            usecase = new DeactivateMemberShipUsecase(memberShipRepository);

            var request = new DeactivateMemberShipRequest(new User("111"), MemberShipType.CJONE);

            assertThrows(MemberShipNotFoundException.class, () -> usecase.apply(request));
        }
    }

}
