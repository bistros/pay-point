package com.kpay.point.api.v1.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ApiResponseView<T> {
  private boolean success;
  private T response;
  private ResponseError error;

  public ApiResponseView(T response) {
    this.response = response;
    this.success = true;
    this.error = null;
  }

  public ApiResponseView(boolean success, T response, ResponseError error) {
    this.success = success;
    this.response = response;
    this.error = error;
  }

  public static ApiResponseView<SuccessStatusViewModel> SUCCESS() {
    return new ApiResponseView<>(true, null, null);
  }

  @Data
  @AllArgsConstructor
  public static class ResponseError {
    private String message;
    private int status;
  }

}
