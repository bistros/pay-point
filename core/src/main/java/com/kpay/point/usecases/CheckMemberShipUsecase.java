package com.kpay.point.usecases;

import com.kpay.point.infrastructure.persistence.MemberShipRepositoryJpa;
import com.kpay.point.infrastructure.persistence.entity.MemberShipEntity;
import com.kpay.point.shared.domain.MemberShip;
import com.kpay.point.shared.domain.MemberShipType;
import com.kpay.point.shared.domain.User;
import com.kpay.point.shared.exception.MemberShipNotFoundException;
import com.kpay.point.shared.model.RequestUsecase;
import com.kpay.point.shared.model.ResponseUsecase;
import com.kpay.point.shared.model.Usecase;
import static com.kpay.point.usecases.CheckMemberShipUsecase.CheckMemberShipRequest;
import static com.kpay.point.usecases.CheckMemberShipUsecase.CheckMemberShipResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * usecase : 멤버쉽 상세 조회하기
 */
@Slf4j
@Service
@AllArgsConstructor
public class CheckMemberShipUsecase implements Usecase<CheckMemberShipRequest, CheckMemberShipResponse> {

  private final MemberShipRepositoryJpa memberShipRepositoryJpa;

  @Override
  @Transactional(readOnly = true)
  public CheckMemberShipResponse apply(CheckMemberShipRequest request) {
    var userId = request.getUser().getId();
    var memberShipId = request.getMemberShipType().getId();

    var entity = memberShipRepositoryJpa
        .findByUserIdAndMembershipId(userId, memberShipId)
        .orElseThrow(() -> new MemberShipNotFoundException("멤버쉽 정보가 없습니다."));
    return new CheckMemberShipResponse(MemberShipEntity.toMemberShip(entity));

  }

  @Getter
  @AllArgsConstructor
  public static class CheckMemberShipRequest implements RequestUsecase {
    private final User user;
    private final MemberShipType memberShipType;
  }

  @Getter
  @AllArgsConstructor
  public static class CheckMemberShipResponse implements ResponseUsecase {
    private final MemberShip memberShip;
  }
}
