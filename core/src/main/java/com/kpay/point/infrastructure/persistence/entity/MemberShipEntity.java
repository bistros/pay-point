package com.kpay.point.infrastructure.persistence.entity;

import com.kpay.point.shared.domain.MemberShip;
import com.kpay.point.shared.domain.MemberShipType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "membership")
@NoArgsConstructor
public class MemberShipEntity extends BaseEntity {

  @Id
  private long seq;

  private String userId;
  private String membershipId;

  private long totalPoint;
  private boolean active = true;

  public MemberShipEntity(String userId, String membershipId, long totalPoint) {
    this.userId = userId;
    this.membershipId = membershipId;
    this.totalPoint = totalPoint;
  }

  public static MemberShip toMemberShip(MemberShipEntity entity) {
    return MemberShip.builder()
        .seq(entity.getSeq())
        .userId(entity.getUserId())
        .memberShipType(MemberShipType.getType(entity.membershipId))
        .startDate(entity.getCreated_at())
        .totalPoint(entity.getTotalPoint())
        .active(entity.isActive())
        .build();
  }
}
