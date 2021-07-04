package com.kpay.point.api;

import com.kpay.point.api.v1.response.ApiResponseView;
import com.kpay.point.api.v1.response.ApiResponseView.ResponseError;
import com.kpay.point.api.v1.response.ViewModel;
import com.kpay.point.shared.exception.AbstractBadRequestException;
import com.kpay.point.shared.exception.AbstractNotFoundException;
import com.kpay.point.shared.exception.KpayException;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(AbstractNotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public ApiResponseView<ViewModel> handleNotFoundException(Throwable t) {
    ResponseError error = new ResponseError(t.getMessage(), NOT_FOUND.value());
    return new ApiResponseView<>(false, null, error);
  }

  @ExceptionHandler(AbstractBadRequestException.class)
  @ResponseStatus(BAD_REQUEST)
  public ApiResponseView<ViewModel> handleBadRequestException(Throwable t) {
    ResponseError error = new ResponseError(t.getMessage(), BAD_REQUEST.value());
    return new ApiResponseView(false, null, error);
  }

  @ExceptionHandler({RuntimeException.class, KpayException.class})
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ApiResponseView<ViewModel> handleInternalException(Throwable t) {
    ResponseError error = new ResponseError(t.getMessage(), INTERNAL_SERVER_ERROR.value());
    return new ApiResponseView(false, null, error);
  }
}
