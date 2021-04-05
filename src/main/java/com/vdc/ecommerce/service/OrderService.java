package com.vdc.ecommerce.service;

import com.vdc.ecommerce.model.OrderDetail;
import com.vdc.ecommerce.model.dto.OrderDTO;
import com.vdc.ecommerce.model.mapper.BaseMapper;
import com.vdc.ecommerce.model.request.OrderRequest;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.service.impl.BaseServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.security.core.Authentication;

public abstract class OrderService extends BaseServiceImpl<OrderDetail, OrderDTO, Long> {
    public OrderService(JpaRepository<OrderDetail, Long> repo, BaseMapper<OrderDetail, OrderDTO> mapper, QuerydslPredicateExecutor<OrderDetail> queryDsl) {
        super(repo, mapper, queryDsl);
    }

    public abstract ResponseModel<String> order(OrderRequest orderRequest, Authentication authentication) throws Exception;
}
