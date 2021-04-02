package com.vdc.ecommerce.controller;

import com.querydsl.core.types.Predicate;
import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.MetricSearch;
import com.vdc.ecommerce.model.predicate.OrderPredicate;
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
    private final OrderPredicate orderPredicate;

    public OrderController(OrderService orderService, OrderPredicate orderPredicate){
        this.orderService = orderService;
        this.orderPredicate = orderPredicate;
    }

    @PostMapping(ApiConstant.ADD)
    public ResponseModel<?> order(@Valid @RequestBody OrderRequest orderRequest) throws Exception {
        return orderService.order(orderRequest);
    }

    @PostMapping(ApiConstant.LIST)
    public ResponseModel<?> getListOrder(@RequestBody(required = false) MetricSearch metricSearch) {
        Predicate predicate = orderPredicate.findByMetricFilter(metricSearch);
        return orderService.findByPredicate(metricSearch, predicate);
    }
}
