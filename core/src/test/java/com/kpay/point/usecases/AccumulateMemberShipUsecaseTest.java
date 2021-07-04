package com.kpay.point.usecases;

import com.kpay.point.infrastructure.persistence.MemberShipLogRepositoryJpa;
import com.kpay.point.infrastructure.persistence.MemberShipRepositoryJpa;
import com.kpay.point.infrastructure.persistence.entity.MemberShipEntity;
import com.kpay.point.shared.domain.MemberShipType;
import com.kpay.point.shared.domain.User;
import com.kpay.point.usecases.AccumulateMemberShipUsecase.AccumulateMemberShipRequest;
import static com.kpay.point.usecases.BaseTestDataMother.ccumulateTestsData;
import static java.util.Optional.empty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

@DisplayName("적립금을 지급할 때")
class AccumulateMemberShipUsecaseTest {


    private AccumulateMemberShipUsecase usecase;
    private MemberShipRepositoryJpa repository;
    private MemberShipLogRepositoryJpa logRepository;

    @BeforeEach
    public void init() {
        repository = mock(MemberShipRepositoryJpa.class);
        logRepository = mock(MemberShipLogRepositoryJpa.class);
    }

    @Test
    @DisplayName("최초 적립일 경우")
    void test1() {
        var verifyObject = new MemberShipEntity("111", "cj", 25);
        verifyObject.setSeq(0L);
        when(repository.findByUserIdAndMembershipId(anyString(), anyString())).thenReturn(empty());
        usecase = new AccumulateMemberShipUsecase(repository, logRepository);

        var request = new AccumulateMemberShipRequest(new User("111"), MemberShipType.CJONE, 2500);
        usecase.apply(request);

        verify(repository, times(1)).save(verifyObject);

    }

    @Test
    @DisplayName("기존 고객 추가 적립")
    void test2() {
        var amount = 2500;
        var mockData = ccumulateTestsData.hasCjPointData();

        when(repository.findByUserIdAndMembershipId(mockData.getUserId(), mockData.getMembershipId()))
                .thenReturn(Optional.of(mockData));
        usecase = new AccumulateMemberShipUsecase(repository, logRepository);

        var request = new AccumulateMemberShipRequest(new User("111"), MemberShipType.CJONE, amount);
        usecase.apply(request);

        mockData.setTotalPoint(mockData.getTotalPoint() + usecase.calculatePoint(amount));
        verify(repository, times(1)).save(mockData);
    }


}