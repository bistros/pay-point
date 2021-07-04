package com.kpay.point.shared.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entity 와 별개인 POJO
 * API layer 와 Application Layer 에서 DTO, VIEW 등과 함께 사용
 */
@Data
@Builder
public class MemberShip {
  private Long seq;
  private String userId;
  private MemberShipType memberShipType;
  private LocalDateTime startDate;
  private long totalPoint;
  private boolean active;
}
