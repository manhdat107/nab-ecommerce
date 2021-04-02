package com.vdc.ecommerce.controller;

import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.MetricSearch;
import com.vdc.ecommerce.model.request.OrderRequest;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.service.OrderService;
import com.vdc.ecommerce.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ApiConstant.CUSTOMER)
@Api(tags = "Customer Controller")
public class CustomerController {


    private final ProductService productService;
    private final OrderService orderService;

    public CustomerController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @PostMapping(ApiConstant.LIST + ApiConstant.PRODUCT)
    public ResponseModel<?> getAllProductDetail(@RequestBody(required = false) MetricSearch metricSearch) {
        return productService.findByPredicate(metricSearch);
    }

    @GetMapping(ApiConstant.PRODUCT)
    public ResponseModel<?> getProductDetail(@RequestParam("id") Long productId) {
        return productService.getById(productId);
    }

    @PostMapping(ApiConstant.ORDER)
    public ResponseModel<?> order(@Valid @RequestBody OrderRequest orderRequest) throws Exception {
        return orderService.order(orderRequest);
    }
}
