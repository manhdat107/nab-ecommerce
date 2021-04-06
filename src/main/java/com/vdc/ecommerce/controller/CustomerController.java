package com.vdc.ecommerce.controller;

import com.querydsl.core.types.Predicate;
import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.MetricSearch;
import com.vdc.ecommerce.model.predicate.ProductPredicate;
import com.vdc.ecommerce.model.request.OrderRequest;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.service.OrderService;
import com.vdc.ecommerce.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ApiConstant.CUSTOMER)
@Api(tags = "Customer Controller")
public class CustomerController {

    private final ProductService productService;
    private final ProductPredicate productPredicate;
    private final OrderService orderService;

    public CustomerController(ProductService productService, ProductPredicate productPredicate, OrderService orderService) {
        this.productService = productService;
        this.productPredicate = productPredicate;
        this.orderService = orderService;
    }

    @ApiOperation(value = "List all product by paging and search condition")
    @PostMapping(ApiConstant.PRODUCT + ApiConstant.LIST)
    public ResponseModel<?> getAllProductDetail(@RequestBody(required = false) MetricSearch metricSearch) {
        Predicate predicate = productPredicate.findByMetricFilter(metricSearch);
        return productService.findByPredicate(metricSearch, predicate);
    }

    @ApiOperation(value = "Product Detail")
    @GetMapping(ApiConstant.PRODUCT)
    public ResponseModel<?> getProductDetail(@RequestParam("id") Long productId) {
        return productService.getById(productId);
    }

    @ApiOperation(value = "Do order a product(s)")
    @PostMapping(ApiConstant.ORDER + ApiConstant.ADD)
    public ResponseModel<?> order(@Valid @RequestBody OrderRequest orderRequest, Authentication authentication) throws Exception {
        return orderService.order(orderRequest, authentication);
    }

    @ApiOperation(value = "Order detail")
    @GetMapping(ApiConstant.ORDER)
    public ResponseModel<?> order(@RequestParam("orderId") Long id) {
        return orderService.getById(id);
    }
}
