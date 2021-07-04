package com.kpay.point.usecases;

import com.kpay.point.infrastructure.persistence.MemberShipRepositoryJpa;
import com.kpay.point.infrastructure.persistence.entity.MemberShipEntity;
import com.kpay.point.shared.domain.MemberShip;
import com.kpay.point.shared.domain.User;
import com.kpay.point.shared.model.RequestUsecase;
import com.kpay.point.shared.model.ResponseUsecase;
import com.kpay.point.shared.model.Usecase;
import static com.kpay.point.usecases.AllCheckMemberShipUsecase.AllCheckMemberRequest;
import static com.kpay.point.usecases.AllCheckMemberShipUsecase.AllCheckMemberResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class AllCheckMemberShipUsecase implements Usecase<AllCheckMemberRequest, AllCheckMemberResponse> {

  private final MemberShipRepositoryJpa memberShipRepositoryJpa;

  @Override
  @Transactional(readOnly = true)
  public AllCheckMemberResponse apply(AllCheckMemberRequest request) {
    var userId = request.getUser().getId();
    var entities = memberShipRepositoryJpa.findByUserIdAndActiveIsTrue(userId);

    var memberships = entities.stream().map(MemberShipEntity::toMemberShip).collect(Collectors.toList());
    return new AllCheckMemberResponse(memberships);
  }


  @Getter
  @AllArgsConstructor
  public static class AllCheckMemberRequest implements RequestUsecase {
    private final User user;
  }

  @Getter
  @AllArgsConstructor
  public static class AllCheckMemberResponse implements ResponseUsecase {
    private final List<MemberShip> memberShips;
  }

}
