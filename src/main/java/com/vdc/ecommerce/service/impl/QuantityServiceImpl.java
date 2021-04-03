package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.model.Quantity;
import com.vdc.ecommerce.model.mapper.QuantityMapper;
import com.vdc.ecommerce.reposirtory.QuantityRepository;
import com.vdc.ecommerce.service.QuantityService;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Service;

@Service
public class QuantityServiceImpl extends QuantityService {

    private final QuantityRepository quantityRepository;

    public QuantityServiceImpl(QuantityRepository repo, QuantityMapper mapper, QuerydslPredicateExecutor<Quantity> queryDsl) {
        super(repo, mapper, queryDsl);
        this.quantityRepository = repo;
    }

    @Override
    public void save(Quantity quantity) {
        if (quantity == null) {
            return;
        }
        quantityRepository.save(quantity);
    }
}
