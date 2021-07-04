package com.kpay.point.shared.exception;

public class AlreadyDeactiveStateException extends AbstractBadRequestException {
  public AlreadyDeactiveStateException(String message) {
    super(message);
  }
}
