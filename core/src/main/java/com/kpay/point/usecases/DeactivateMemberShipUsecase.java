package com.kpay.point.usecases;

import com.kpay.point.infrastructure.persistence.MemberShipRepositoryJpa;
import com.kpay.point.shared.domain.MemberShipType;
import com.kpay.point.shared.domain.User;
import com.kpay.point.shared.exception.AlreadyDeactiveStateException;
import com.kpay.point.shared.exception.MemberShipNotFoundException;
import com.kpay.point.shared.model.RequestUsecase;
import com.kpay.point.shared.model.Usecase;
import com.kpay.point.shared.model.VoidResponseUsecase;
import static com.kpay.point.shared.model.VoidResponseUsecase.VOID;
import static com.kpay.point.usecases.DeactivateMemberShipUsecase.DeactivateMemberShipRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * usecase : 멤버쉽 정보를 비활성화(삭제) 한다
 */
@Slf4j
@Service
@AllArgsConstructor
public class DeactivateMemberShipUsecase implements Usecase<DeactivateMemberShipRequest, VoidResponseUsecase> {

    private final MemberShipRepositoryJpa repositoryJpa;

    @Override
    @Transactional
    public VoidResponseUsecase apply(DeactivateMemberShipRequest request) {
        var userId = request.getUser().getId();
        var memberShipId = request.getMemberShipType().getId();

        var memberShip = repositoryJpa.findByUserIdAndMembershipId(userId, memberShipId)
                .orElseThrow(() -> new MemberShipNotFoundException("멤버쉽 정보가 없기 떄문에 비활성화를 실패하였습니다"));

        if (!memberShip.isActive()) {
            throw new AlreadyDeactiveStateException("이미 비활성화 상태이기 때문에 다시 상태를 변경 할 수 없습니다");
        }

        memberShip.setActive(false);
        repositoryJpa.save(memberShip);

        return VOID;
    }


    @Data
    @AllArgsConstructor
    public static class DeactivateMemberShipRequest implements RequestUsecase {
        private final User user;
        private final MemberShipType memberShipType;
    }


}
