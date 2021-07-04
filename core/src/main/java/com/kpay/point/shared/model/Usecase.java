package com.kpay.point.shared.model;

/**
 * 각 Usecase 는 이 인터페이스를 구현해야한다.
 *
 * @param <T> Request 로 RequestUsecase interface 를 가진다
 * @param <R> Response 으로 ResponseUsecase interface 를 가진다
 */
public interface Usecase<T extends RequestUsecase, R extends ResponseUsecase> {
    R apply(T t);
}
