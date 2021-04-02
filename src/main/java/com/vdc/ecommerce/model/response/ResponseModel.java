package com.vdc.ecommerce.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vdc.ecommerce.common.DateConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Slf4j
public class ResponseModel<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Meta {
        @JsonIgnore
        private final SimpleDateFormat format;
        public String serverDateTime;
        public int statusCode;
        public String message;
        public List<String> messages;

        Meta(int statusCode, String message) {
            format = new SimpleDateFormat(DateConstant.DATE_TIME.getType());
            serverDateTime = format.format(new Date());
            this.statusCode = statusCode;
            this.message = message;
        }

        Meta(int statusCode, List<String> messages) {
            format = new SimpleDateFormat(DateConstant.DATE_TIME.getType());
            serverDateTime = format.format(new Date());
            this.statusCode = statusCode;
            this.messages = messages;
        }
    }

    private Meta meta;
    private T data;
    private ResponsePageableModel<?> pagination;

    //prevent init this object
    private ResponseModel() {
    }

    public static <T> ResponseModel<List<T>> successful(String message, ResponsePageableModel<T> pagination) {
        ResponseModel<List<T>> response = new ResponseModel<>();
        response.setMeta(new Meta(HttpStatus.OK.value(), message));
        response.setData(pagination.getContent());
        response.setPagination(pagination);
        return response;
    }

    public static <T> ResponseModel<T> successful(String message, T data) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setMeta(new Meta(HttpStatus.OK.value(), message));
        response.setData(data);
        return response;
    }


    public static <T> ResponseModel<T> successful(String message) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setMeta(new Meta(HttpStatus.OK.value(), message));
        return response;
    }

    public static <T> ResponseModel<T> failure(String message, int statusCode) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setMeta(new Meta(statusCode, message));
        return response;
    }

    public static <T> ResponseModel<T> failure(String message) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setMeta(new Meta(HttpStatus.BAD_REQUEST.value(), message));
        return response;
    }

    public static <T> ResponseModel<T> failure(List<String> messages) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setMeta(new Meta(HttpStatus.BAD_REQUEST.value(), messages));
        return response;
    }
}


