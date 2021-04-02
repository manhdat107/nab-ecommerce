package com.vdc.ecommerce.controller;

import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.MetricSearch;
import com.vdc.ecommerce.model.request.OrderRequest;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "Order Controller")
@RequestMapping(ApiConstant.ORDER)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping(ApiConstant.ADD)
    public ResponseModel<?> order(@Valid @RequestBody OrderRequest orderRequest) throws Exception {
        return orderService.order(orderRequest);
    }

    @PostMapping(ApiConstant.LIST)
    public ResponseModel<?> getListOrder(@RequestBody(required = false) MetricSearch orderRequest) {
        return null;
    }
}
