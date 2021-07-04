package com.kpay.point.api.v1.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class RegistMemberShipCommand {
  private String membershipId;
  private String membershipName;
  @Min(1)
  private int point;
}
