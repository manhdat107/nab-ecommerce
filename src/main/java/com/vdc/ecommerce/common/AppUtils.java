package com.vdc.ecommerce.common;

import com.vdc.ecommerce.model.BaseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;

public class AppUtils {
    public static Pageable getPageable(Integer page, Integer size, String field, Boolean isDesc) {
        if (field == null || field.isEmpty() || isDesc == null) {
            return PageRequest.of(page == null ? 0 : page, size == null ? PageConstant.PAGE_SIZE_DEFAULT.getNum() : size);
        }
        return PageRequest.of(page == null ? 0 : page, size == null ? PageConstant.PAGE_SIZE_DEFAULT.getNum() : size,
                isDesc ? Sort.Direction.DESC : Sort.Direction.ASC, field);
    }

    public static <E extends BaseEntity<Long>> boolean checkFieldInObj(String field, E obj) throws NoSuchFieldException {
        Field a = obj.getClass().getField(field);
        return false;
    }

}
