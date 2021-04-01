package com.vdc.ecommerce.model.dto;

import com.vdc.ecommerce.model.Branch;
import com.vdc.ecommerce.model.Color;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductDTO extends BaseDTO<Long> {

    private String name;

    private Color color;

    private BigDecimal price;

    private Branch branch;
}
