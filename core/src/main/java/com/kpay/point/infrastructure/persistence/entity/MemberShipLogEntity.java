package com.kpay.point.infrastructure.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 멤버쉽 적립 건별로 로그를 적재하기 위한 Entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "membership_log")
@NoArgsConstructor
public class MemberShipLogEntity extends BaseEntity {

  @Id
  @GeneratedValue
  private Long seq;
  private String userId;
  private String memberShipId;
  private int amount;

  public MemberShipLogEntity(String userId, String memberShipId, int amount) {
    this.userId = userId;
    this.memberShipId = memberShipId;
    this.amount = amount;
  }
}
