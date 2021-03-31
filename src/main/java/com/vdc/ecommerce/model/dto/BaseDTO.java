package com.vdc.ecommerce.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseDTO<ID> {

    private ID id;
}