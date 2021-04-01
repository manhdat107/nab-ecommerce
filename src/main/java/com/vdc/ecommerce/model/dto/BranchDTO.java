package com.vdc.ecommerce.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchDTO extends BaseDTO<Long> {

    private String name;

    private String description;

}
