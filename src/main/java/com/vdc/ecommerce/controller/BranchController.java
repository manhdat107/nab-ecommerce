package com.vdc.ecommerce.controller;

import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.dto.BranchDTO;
import com.vdc.ecommerce.model.response.JsonResponseEntity;
import com.vdc.ecommerce.service.BranchService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.BRANCH)
@Api(tags = "Branch management")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping(ApiConstant.LIST)
    public JsonResponseEntity<?> getAllBranch(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        return branchService.getAll(pageNumber, pageSize);
    }

    @PostMapping(ApiConstant.ADD)
    public JsonResponseEntity<?> addNewBranch(@RequestBody BranchDTO branchDTO) {
        return branchService.add(branchDTO);
    }

    @DeleteMapping(ApiConstant.DELETE + "/{id}")
    public JsonResponseEntity<?> deleteBranch(@PathVariable("id") Long id) {
        return branchService.deleteById(id);
    }
}
