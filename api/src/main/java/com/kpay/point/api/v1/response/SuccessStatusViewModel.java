package com.kpay.point.api.v1.response;

import lombok.Data;

@Data
public class SuccessStatusViewModel implements ViewModel {
  private boolean success;
}
