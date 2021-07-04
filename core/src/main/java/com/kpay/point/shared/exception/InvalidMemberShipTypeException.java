package com.kpay.point.shared.exception;

public class InvalidMemberShipTypeException extends AbstractBadRequestException {
  public InvalidMemberShipTypeException(String id) {
    super(id + "는 존재하지 않는 멤머쉽 종류입니다");
  }
}
