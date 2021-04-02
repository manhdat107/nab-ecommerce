package com.vdc.ecommerce.config.exceptionhandle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ValidException extends RuntimeException {

    public ValidException(String exception) {
        super(exception);
    }
}
