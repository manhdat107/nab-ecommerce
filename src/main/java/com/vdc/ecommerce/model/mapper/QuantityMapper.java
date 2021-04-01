package com.vdc.ecommerce.model.mapper;


import com.vdc.ecommerce.model.Quantity;
import com.vdc.ecommerce.model.dto.QuantityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuantityMapper extends BaseMapper<Quantity, QuantityDTO> {
}
