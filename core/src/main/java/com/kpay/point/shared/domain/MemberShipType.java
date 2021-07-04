package com.kpay.point.shared.domain;

import com.kpay.point.shared.exception.InvalidMemberShipTypeException;

import java.util.Arrays;

public enum MemberShipType {
  HAPPYPOINT("spc", "happypoint"),
  SSG("shinsegae", "shinsegaepoint"),
  CJONE("cj", "cjone");

  private String id;
  private String name;

  MemberShipType(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public static MemberShipType getType(String id) {
    return Arrays.stream(values()).filter(c -> id.equals(c.getId())).findFirst()
        .orElseThrow(() -> new InvalidMemberShipTypeException(id));
  }
}
