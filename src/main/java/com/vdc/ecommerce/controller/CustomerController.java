package com.vdc.ecommerce.controller;

import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.MetricSearch;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.CUSTOMER)
@Api(tags = "Customer Controller")
public class CustomerController {

    private final ProductService productService;

    public CustomerController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(ApiConstant.LIST + ApiConstant.PRODUCT)
    public ResponseModel<?> getAllProductDetail(@RequestBody(required = false) MetricSearch metricSearch) {
        return productService.findByPredicate(metricSearch);
    }

    @GetMapping(ApiConstant.PRODUCT)
    public ResponseModel<?> getProductDetail(@RequestParam("id") Long productId) {
        return productService.getById(productId);
    }
}
