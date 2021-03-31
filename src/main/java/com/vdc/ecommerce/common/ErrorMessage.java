package com.vdc.ecommerce.common;


import lombok.Getter;

@Getter
public enum  ErrorMessage {
    JWT_INVALID_SIG(500, "Invalid JWT signature"),
    JWT_INVALID_TOKEN(500, "Invalid JWT token"),
    JWT_EXPIRED(500, "Expired JWT token"),
    JWT_UNSUPPORTED(500, "Unsupported JWT token"),
    JWT_EMPTY(500, "JWT claims string is empty"),
    AUTH_ROLE_NOT_FIND(500, "Fail! -> Cause: User Role not find"),
    AUTH_WRONG_INFORMATION(500, "Wrong username or password");

    public final int statusCode;
    public final String message;

    ErrorMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}

