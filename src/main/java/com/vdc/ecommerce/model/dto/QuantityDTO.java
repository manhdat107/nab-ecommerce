package com.vdc.ecommerce.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuantityDTO extends BaseDTO<Long>{
    private Long quantity;
}
