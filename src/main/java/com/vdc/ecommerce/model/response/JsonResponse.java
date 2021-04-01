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
public class JsonResponse<T> {

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
    private RestResponsePage<?> pagination;

    //prevent init this object
    private JsonResponse() {
    }

    public static <T> JsonResponse<List<T>> successful(String message, RestResponsePage<T> pagination) {
        JsonResponse<List<T>> response = new JsonResponse<>();
        response.setMeta(new Meta(HttpStatus.OK.value(), message));
        response.setData(pagination.getContent());
        response.setPagination(pagination);
        return response;
    }

    public static <T> JsonResponse<T> successful(String message, T data) {
        JsonResponse<T> response = new JsonResponse<>();
        response.setMeta(new Meta(HttpStatus.OK.value(), message));
        response.setData(data);
        return response;
    }


    public static <T> JsonResponse<T> successful(String message) {
        JsonResponse<T> response = new JsonResponse<>();
        response.setMeta(new Meta(HttpStatus.OK.value(), message));
        return response;
    }

    public static <T> JsonResponse<T> failure(String message, int statusCode) {
        JsonResponse<T> response = new JsonResponse<>();
        response.setMeta(new Meta(statusCode, message));
        return response;
    }

    public static <T> JsonResponse<T> failure(String message) {
        JsonResponse<T> response = new JsonResponse<>();
        response.setMeta(new Meta(HttpStatus.BAD_REQUEST.value(), message));
        return response;
    }
}


