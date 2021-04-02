package com.vdc.ecommerce.service;

import com.vdc.ecommerce.common.AppUtils;
import com.vdc.ecommerce.model.Branch;
import com.vdc.ecommerce.model.dto.BranchDTO;
import com.vdc.ecommerce.model.mapper.BaseMapper;
import com.vdc.ecommerce.service.impl.BaseServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public abstract class BranchService extends BaseServiceImpl<Branch, BranchDTO, Long> {

    public BranchService(JpaRepository<Branch, Long> repo, BaseMapper<Branch, BranchDTO> mapper, AppUtils appUtils,
                         QuerydslPredicateExecutor<Branch> queryDsl) {
        super(repo, mapper, appUtils, queryDsl);
    }
}
