package com.vdc.ecommerce.model.mapper;

import com.vdc.ecommerce.model.Product;
import com.vdc.ecommerce.model.dto.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductDTO> {
}
