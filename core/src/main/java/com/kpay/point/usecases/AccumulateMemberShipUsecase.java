package com.kpay.point.usecases;

import com.kpay.point.infrastructure.persistence.MemberShipLogRepositoryJpa;
import com.kpay.point.infrastructure.persistence.MemberShipRepositoryJpa;
import com.kpay.point.infrastructure.persistence.entity.MemberShipEntity;
import com.kpay.point.infrastructure.persistence.entity.MemberShipLogEntity;
import com.kpay.point.shared.domain.MemberShipType;
import com.kpay.point.shared.domain.User;
import com.kpay.point.shared.model.RequestUsecase;
import com.kpay.point.shared.model.Usecase;
import com.kpay.point.shared.model.VoidResponseUsecase;
import static com.kpay.point.shared.model.VoidResponseUsecase.VOID;
import static com.kpay.point.usecases.AccumulateMemberShipUsecase.AccumulateMemberShipRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class AccumulateMemberShipUsecase implements Usecase<AccumulateMemberShipRequest, VoidResponseUsecase> {

    private final MemberShipRepositoryJpa repositoryJpa;
    private final MemberShipLogRepositoryJpa logRepositoryJpa;

    @Override
    @Transactional
    public VoidResponseUsecase apply(AccumulateMemberShipRequest request) {
        var userId = request.getUser().getId();
        var memberShipId = request.getMemberShipType().getId();

        var memberShipEntity = repositoryJpa.findByUserIdAndMembershipId(userId, memberShipId);
        var point = calculatePoint(request.getAmount());


        //TODO : 존재하는데 비활성화 상태일 경우, 존재하지 않는데 첫 고객일경우 처리해야함
        memberShipEntity.ifPresentOrElse(
                entity -> {
                    entity.setTotalPoint(entity.getTotalPoint() + point);
                    repositoryJpa.save(entity);
                },
                () -> {
                    MemberShipEntity entity = new MemberShipEntity();
                    entity.setUserId(userId);
                    entity.setTotalPoint(point);
                    entity.setMembershipId(memberShipId);
                    repositoryJpa.save(entity);
                }
        );


        logRepositoryJpa.save(new MemberShipLogEntity(userId, memberShipId, request.getAmount()));

        return VOID;
    }

    protected int calculatePoint(long amount) {
        if (amount <= 0) {
            return 0;
        }
        return (int) Math.ceil(amount * 0.01);
    }

    @Getter
    @AllArgsConstructor
    public static class AccumulateMemberShipRequest implements RequestUsecase {
        private final User user;
        private final MemberShipType memberShipType;
        private final int amount;
    }

}
