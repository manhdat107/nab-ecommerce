package com.vdc.ecommerce.controller;

import com.querydsl.core.types.Predicate;
import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.MetricSearch;
import com.vdc.ecommerce.model.dto.ProductDTO;
import com.vdc.ecommerce.model.predicate.OrderPredicate;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.service.OrderService;
import com.vdc.ecommerce.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.ADMIN)
@Api(tags = "Product Management")
public class AdminController {

    private final ProductService productService;
    private final OrderPredicate orderPredicate;
    private final OrderService orderService;


    public AdminController(ProductService productService, OrderPredicate orderPredicate, OrderService orderService) {
        this.productService = productService;
        this.orderPredicate = orderPredicate;
        this.orderService = orderService;
    }

    @PostMapping(ApiConstant.PRODUCT + ApiConstant.ADD)
    public ResponseModel<?> addNewProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @PutMapping(ApiConstant.PRODUCT + ApiConstant.UPDATE)
    public ResponseModel<?> updateProduct(@RequestBody ProductDTO productDTO) {
        return productService.update(productDTO);
    }

    @DeleteMapping(ApiConstant.PRODUCT + ApiConstant.DELETE)
    public ResponseModel<?> deleteProduct(@RequestParam("id") Long productId) {
        return productService.deleteById(productId);
    }

    @PutMapping(ApiConstant.PRODUCT + ApiConstant.UPDATE + ApiConstant.QUANTITY)
    public ResponseModel<?> updateProductQuantity(@RequestParam Long productId, @RequestParam Long quantity) {
        return productService.updateQuantity(productId, quantity);
    }

    @PostMapping(ApiConstant.ORDER + ApiConstant.LIST)
    public ResponseModel<?> getListOrder(@RequestBody(required = false) MetricSearch metricSearch) {
        Predicate predicate = orderPredicate.findByMetricFilter(metricSearch);
        return orderService.findByPredicate(metricSearch, predicate);
    }
}
