package com.vdc.ecommerce.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OrderDTO extends BaseDTO<Long> {

    private Set<ProductDTO> products;

    private String fullname;

    private String address;

    private Long phoneNumber;

    private String email;
}
