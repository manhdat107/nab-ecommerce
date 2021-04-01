package com.vdc.ecommerce.service;

import com.vdc.ecommerce.model.Quantity;
import com.vdc.ecommerce.model.dto.QuantityDTO;
import com.vdc.ecommerce.model.mapper.QuantityMapper;
import com.vdc.ecommerce.reposirtory.QuantityRepository;
import com.vdc.ecommerce.service.impl.BaseServiceImpl;

public abstract class QuantityService extends BaseServiceImpl<Quantity, QuantityDTO, Long> {
    public QuantityService(QuantityRepository repo, QuantityMapper baseMapper) {
        super(repo, baseMapper);
    }

    public abstract void save(Quantity quantity);
}
