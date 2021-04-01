package com.vdc.ecommerce.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class AppUtils {
    public Pageable getPageable(Integer page, Integer size, String field, Boolean isDesc) {
        if (field == null || field.isEmpty() || isDesc == null) {
            return PageRequest.of(page == null ? 0 : page, size == null ? PageConstant.PAGE_SIZE_DEFAULT.getNum() : size);
        }
        return PageRequest.of(page == null ? 0 : page, size == null ? PageConstant.PAGE_SIZE_DEFAULT.getNum() : size,
                isDesc ? Sort.Direction.DESC : Sort.Direction.ASC, field);
    }
}