package com.vdc.ecommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO extends BaseDTO<Long> {

    private String name;

    private String description;

}
