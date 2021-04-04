package com.vdc.ecommerce.config.exceptionhandle;


import com.vdc.ecommerce.model.response.ResponseModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseModel<?> handleAllExceptions(Exception ex, WebRequest request) {
        return ResponseModel.failure(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public final ResponseModel<?> badCredentialsException(Exception ex, WebRequest request) {
        return ResponseModel.failure(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(ValidException.class)
    public final ResponseModel<?> handleUserNotFoundException(ValidException ex, WebRequest request) {
        return ResponseModel.failure(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND.value());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        return new ResponseEntity(ResponseModel.failure(details), HttpStatus.BAD_REQUEST);
    }
}