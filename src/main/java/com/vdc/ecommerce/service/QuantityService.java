package com.vdc.ecommerce.service;

import com.vdc.ecommerce.common.AppUtils;
import com.vdc.ecommerce.model.Quantity;
import com.vdc.ecommerce.model.dto.QuantityDTO;
import com.vdc.ecommerce.model.mapper.QuantityMapper;
import com.vdc.ecommerce.reposirtory.QuantityRepository;
import com.vdc.ecommerce.service.impl.BaseServiceImpl;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public abstract class QuantityService extends BaseServiceImpl<Quantity, QuantityDTO, Long> {
    public QuantityService(QuantityRepository repo, QuantityMapper mapper, AppUtils appUtils, QuerydslPredicateExecutor<Quantity> queryDsl) {
        super(repo, mapper, appUtils, queryDsl);
    }

    public abstract void save(Quantity quantity);
}
