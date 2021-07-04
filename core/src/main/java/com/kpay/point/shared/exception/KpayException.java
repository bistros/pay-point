package com.kpay.point.shared.exception;

public class KpayException extends RuntimeException {
  public KpayException() {
  }

  public KpayException(String message) {
    super(message);
  }

  public KpayException(String message, Throwable cause) {
    super(message, cause);
  }

  public KpayException(Throwable cause) {
    super(cause);
  }

  public KpayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
