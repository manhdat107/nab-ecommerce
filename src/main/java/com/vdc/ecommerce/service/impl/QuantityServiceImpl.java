package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.common.AppUtils;
import com.vdc.ecommerce.model.Quantity;
import com.vdc.ecommerce.model.mapper.QuantityMapper;
import com.vdc.ecommerce.reposirtory.QuantityRepository;
import com.vdc.ecommerce.service.QuantityService;
import org.springframework.stereotype.Service;

@Service
public class QuantityServiceImpl extends QuantityService {

    public QuantityServiceImpl(QuantityRepository repo, QuantityMapper mapper, QuantityRepository queryDsl, AppUtils appUtils) {
        super(repo, mapper, queryDsl, appUtils);
    }

    @Override
    public void save(Quantity quantity) {
        if (quantity == null) {
            return;
        }
        repo.save(quantity);
    }
}
