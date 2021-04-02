package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.common.AppUtils;
import com.vdc.ecommerce.model.mapper.BranchMapper;
import com.vdc.ecommerce.reposirtory.BranchRepository;
import com.vdc.ecommerce.service.BranchService;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceImpl extends BranchService {

    public BranchServiceImpl(BranchRepository repo, BranchMapper mapper, AppUtils appUtils) {
        super(repo, mapper, appUtils);
    }
}
