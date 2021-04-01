package com.vdc.ecommerce.common;

import lombok.Getter;

@Getter
public enum PageConstant {
    PAGE_DEFAULT(0),

    PAGE_SIZE_DEFAULT(10);

    private final int num;

    PageConstant(int num) {
        this.num = num;
    }
}
