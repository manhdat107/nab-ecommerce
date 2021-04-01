package com.vdc.ecommerce.model.mapper;

import com.vdc.ecommerce.model.Product;
import com.vdc.ecommerce.model.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductDTO> {
    @Override
    @Mapping(source = "quantity", target = "quantityDTO")
    ProductDTO toDTO(Product product);
}
