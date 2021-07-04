package com.kpay.point.shared.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class User {
    @NotNull
    private String id;
}
