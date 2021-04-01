package com.vdc.ecommerce.service;

import com.vdc.ecommerce.model.Product;
import com.vdc.ecommerce.model.dto.ProductDTO;
import com.vdc.ecommerce.model.mapper.ProductMapper;
import com.vdc.ecommerce.reposirtory.ProductRepository;
import com.vdc.ecommerce.service.impl.BaseServiceImpl;

public abstract class ProductService extends BaseServiceImpl<Product, ProductDTO, Long> {

    public ProductService(ProductRepository repository, ProductMapper productMapper) {
        super(repository, productMapper);
    }
}
