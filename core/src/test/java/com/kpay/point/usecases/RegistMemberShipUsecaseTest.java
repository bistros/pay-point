package com.kpay.point.usecases;

import com.kpay.point.infrastructure.persistence.MemberShipRepositoryJpa;
import com.kpay.point.infrastructure.persistence.entity.MemberShipEntity;
import com.kpay.point.shared.domain.MemberShipType;
import com.kpay.point.shared.domain.User;
import com.kpay.point.shared.exception.AlreadyExistMemberShipException;
import com.kpay.point.shared.model.Usecase;
import static com.kpay.point.usecases.BaseTestDataMother.RegistTestData;
import com.kpay.point.usecases.RegistMemberShipUsecase.RegistMemberShipRequest;
import static com.kpay.point.usecases.RegistMemberShipUsecase.RegistMemberShipResponse;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("멤버쉽을 신규로 등록 할 때 ")
class RegistMemberShipUsecaseTest {

    private Usecase<RegistMemberShipRequest, RegistMemberShipResponse> usecase;
    private MemberShipRepositoryJpa repository;

    @BeforeEach
    public void init() {
        repository = mock(MemberShipRepositoryJpa.class);
    }

    @Test
    @DisplayName("이미 멤버쉽 정보가 있으면 예외 발생")
    public void test1() {
        var memberShip = new MemberShipEntity("111", "cj", 999_000);
        when(repository
                .findByUserIdAndMembershipId(memberShip.getUserId(), memberShip.getMembershipId()))
                .thenReturn(of(memberShip));
        usecase = new RegistMemberShipUsecase(repository);

        var request = new RegistMemberShipRequest(new User("111"), MemberShipType.CJONE, 100);
        assertThrows(AlreadyExistMemberShipException.class, () -> usecase.apply(request));
    }

    @Test
    @DisplayName("DeActive 정보가 있더라도 예외 발생")
    public void test2() {
        var memberShip = RegistTestData.registedDeActiveData();
        when(repository
                .findByUserIdAndMembershipId(memberShip.getUserId(), memberShip.getMembershipId()))
                .thenReturn(of(memberShip));
        usecase = new RegistMemberShipUsecase(repository);

        var request = new RegistMemberShipRequest(new User("111"), MemberShipType.CJONE, 100);
        assertThrows(AlreadyExistMemberShipException.class, () -> usecase.apply(request));
    }

}