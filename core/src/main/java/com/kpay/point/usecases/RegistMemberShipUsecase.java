package com.kpay.point.usecases;

import com.kpay.point.infrastructure.persistence.MemberShipRepositoryJpa;
import com.kpay.point.infrastructure.persistence.entity.MemberShipEntity;
import com.kpay.point.shared.domain.MemberShip;
import com.kpay.point.shared.domain.MemberShipType;
import com.kpay.point.shared.domain.User;
import com.kpay.point.shared.exception.AlreadyExistMemberShipException;
import com.kpay.point.shared.model.RequestUsecase;
import com.kpay.point.shared.model.ResponseUsecase;
import com.kpay.point.shared.model.Usecase;
import static com.kpay.point.usecases.RegistMemberShipUsecase.RegistMemberShipRequest;
import static com.kpay.point.usecases.RegistMemberShipUsecase.RegistMemberShipResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class RegistMemberShipUsecase implements Usecase<RegistMemberShipRequest, RegistMemberShipResponse> {

  private final MemberShipRepositoryJpa memberShipRepository;

  @Override
  @Transactional
  public RegistMemberShipResponse apply(RegistMemberShipRequest request) {
    var userId = request.getUser().getId();
    var memberShipId = request.getMemberShipType().getId();
    var point = request.getPoint();

    memberShipRepository
        .findByUserIdAndMembershipId(userId, memberShipId)
        .ifPresent(memberShipEntity -> {
          throw new AlreadyExistMemberShipException("이미 멤버쉽 정보가 존재하기 때문에 신규 등록할 수 없습니다");
        });

    var entity = memberShipRepository.save(new MemberShipEntity(userId, memberShipId, point));
    return new RegistMemberShipResponse(MemberShipEntity.toMemberShip(entity));
  }

  @Data
  @AllArgsConstructor
  public static class RegistMemberShipRequest implements RequestUsecase {
    private final User user;
    private final MemberShipType memberShipType;
    private final int point;
  }

  @Data
  @AllArgsConstructor
  public static class RegistMemberShipResponse implements ResponseUsecase {
    private final MemberShip memberShip;
  }
}
