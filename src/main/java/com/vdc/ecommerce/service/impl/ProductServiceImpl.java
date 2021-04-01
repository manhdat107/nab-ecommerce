package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.model.mapper.ProductMapper;
import com.vdc.ecommerce.reposirtory.ProductRepository;
import com.vdc.ecommerce.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ProductService {

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper) {
        super(repository, mapper);
    }

}
