package com.vdc.ecommerce.model.mapper;

import com.vdc.ecommerce.model.OrderDetail;
import com.vdc.ecommerce.model.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<OrderDetail, OrderDTO> {
}
