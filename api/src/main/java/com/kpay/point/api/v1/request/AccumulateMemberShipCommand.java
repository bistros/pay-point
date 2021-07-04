package com.kpay.point.api.v1.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccumulateMemberShipCommand {

  @NotNull
  private String membershipId;

  @NotNull
  private int amount;
}
