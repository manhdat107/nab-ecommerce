package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.model.Quantity;
import com.vdc.ecommerce.model.mapper.QuantityMapper;
import com.vdc.ecommerce.reposirtory.QuantityRepository;
import com.vdc.ecommerce.service.QuantityService;
import org.springframework.stereotype.Service;

@Service
public class QuantityServiceImpl extends QuantityService {
    public QuantityServiceImpl(QuantityRepository repo, QuantityMapper mapper) {
        super(repo, mapper);
    }

    @Override
    public void save(Quantity quantity) {
        if (quantity == null) {
            return;
        }
        repo.save(quantity);
    }
}
