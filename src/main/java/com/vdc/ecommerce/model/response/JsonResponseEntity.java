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
@Setter
@Getter
@Slf4j
public class JsonResponseEntity<T> {

    private static class Meta {
        @JsonIgnore
        private final SimpleDateFormat format;
        public String serverDateTime;
        public int statusCode;
        public String message;

        Meta(int statusCode, String message) {
            format = new SimpleDateFormat(DateConstant.DATE_TIME.getType());
            serverDateTime = format.format(new Date());
            this.statusCode = statusCode;
            this.message = message;
        }
    }

    private Meta meta;
    private T data;
    private ResponsePageable<?> pagination;

    //prevent init this object
    private JsonResponseEntity() {
    }

    public static <T> JsonResponseEntity<List<T>> successful(String message, ResponsePageable<T> pagination) {
        JsonResponseEntity<List<T>> response = new JsonResponseEntity<>();
        response.setMeta(new Meta(HttpStatus.OK.value(), message));
        response.setData(pagination.getContent());
        response.setPagination(pagination);
        return response;
    }

    public static <T> JsonResponseEntity<T> successful(String message, T data) {
        JsonResponseEntity<T> response = new JsonResponseEntity<>();
        response.setMeta(new Meta(HttpStatus.OK.value(), message));
        response.setData(data);
        return response;
    }


    public static <T> JsonResponseEntity<T> successful(String message) {
        JsonResponseEntity<T> response = new JsonResponseEntity<>();
        response.setMeta(new Meta(HttpStatus.OK.value(), message));
        return response;
    }

    public static <T> JsonResponseEntity<T> failure(String message, int statusCode) {
        JsonResponseEntity<T> response = new JsonResponseEntity<>();
        response.setMeta(new Meta(statusCode, message));
        return response;
    }

    public static <T> JsonResponseEntity<T> failure(String message) {
        JsonResponseEntity<T> response = new JsonResponseEntity<>();
        response.setMeta(new Meta(HttpStatus.BAD_REQUEST.value(), message));
        return response;
    }
}


