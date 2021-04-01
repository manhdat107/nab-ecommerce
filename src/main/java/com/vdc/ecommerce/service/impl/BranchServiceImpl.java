package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.model.Branch;
import com.vdc.ecommerce.model.dto.BranchDTO;
import com.vdc.ecommerce.model.mapper.BaseMapper;
import com.vdc.ecommerce.service.BranchService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceImpl extends BranchService {

    public BranchServiceImpl(JpaRepository<Branch, Long> repo, BaseMapper<Branch, BranchDTO> mapper) {
        super(repo, mapper);
    }
}
