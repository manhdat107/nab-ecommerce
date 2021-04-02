package com.vdc.ecommerce.controller;

import com.querydsl.core.types.Predicate;
import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.MetricSearch;
import com.vdc.ecommerce.model.predicate.ProductPredicate;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.CUSTOMER)
@Api(tags = "Customer Controller")
public class CustomerController {

    private final ProductService productService;
    private final ProductPredicate productPredicate;

    public CustomerController(ProductService productService, ProductPredicate productPredicate) {
        this.productService = productService;
        this.productPredicate = productPredicate;
    }

    @PostMapping(ApiConstant.LIST + ApiConstant.PRODUCT)
    public ResponseModel<?> getAllProductDetail(@RequestBody(required = false) MetricSearch metricSearch) {
        Predicate predicate = productPredicate.findByMetricFilter(metricSearch);
        return productService.findByPredicate(metricSearch, predicate);
    }

    @GetMapping(ApiConstant.PRODUCT)
    public ResponseModel<?> getProductDetail(@RequestParam("id") Long productId) {
        return productService.getById(productId);
    }
}
