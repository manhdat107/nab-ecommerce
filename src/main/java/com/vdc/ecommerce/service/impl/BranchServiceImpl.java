package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.model.Branch;
import com.vdc.ecommerce.model.mapper.BranchMapper;
import com.vdc.ecommerce.reposirtory.BranchRepository;
import com.vdc.ecommerce.service.BranchService;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceImpl extends BranchService {

    public BranchServiceImpl(BranchRepository repo, BranchMapper mapper,
                             QuerydslPredicateExecutor<Branch> queryDsl) {
        super(repo, mapper, queryDsl);
    }
}
