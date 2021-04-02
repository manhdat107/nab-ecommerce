package com.vdc.ecommerce.service;

import com.vdc.ecommerce.common.AppUtils;
import com.vdc.ecommerce.model.Product;
import com.vdc.ecommerce.model.dto.ProductDTO;
import com.vdc.ecommerce.model.mapper.ProductMapper;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.reposirtory.ProductRepository;
import com.vdc.ecommerce.service.impl.BaseServiceImpl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public abstract class ProductService extends BaseServiceImpl<Product, ProductDTO, Long> {

    public ProductService(ProductRepository repository, ProductMapper productMapper, AppUtils appUtils, QuerydslPredicateExecutor<Product> queryDsl) {
        super(repository, productMapper, appUtils, queryDsl);
    }

    public abstract ResponseModel<String> addProduct(ProductDTO productDTO);

    public abstract ResponseModel<String> updateQuantity(Long productId, Long quantity);

//    public abstract ResponseModel<List<ProductDTO>> findByPredicate(MetricSearch metricSearch);

    public abstract List<Product> findByIds(List<Long> ids);

    public abstract void updateList(List<Product> products);
}
