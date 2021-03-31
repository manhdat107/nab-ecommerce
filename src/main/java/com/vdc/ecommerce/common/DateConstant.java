package com.vdc.ecommerce.common;


import lombok.Getter;

@Getter
public enum DateConstant {
    DATE("yyyy-MM-dd"),
    DATE_TIME("yyyy-MM-dd hh:mm:ss");
    private final String type;

    DateConstant(String type) {
        this.type = type;
    }
}
