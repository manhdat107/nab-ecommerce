package com.vdc.ecommerce.service;

import com.vdc.ecommerce.model.Branch;
import com.vdc.ecommerce.model.dto.BranchDTO;
import com.vdc.ecommerce.model.mapper.BaseMapper;
import com.vdc.ecommerce.service.impl.BaseServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BranchService extends BaseServiceImpl<Branch, BranchDTO, Long> {
    public BranchService(JpaRepository<Branch, Long> repo, BaseMapper<Branch, BranchDTO> mapper) {
        super(repo, mapper);
    }
}
