package com.vdc.ecommerce.model.mapper;

import com.vdc.ecommerce.model.Branch;
import com.vdc.ecommerce.model.dto.BranchDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BranchMapper extends BaseMapper<Branch, BranchDTO>{
}
