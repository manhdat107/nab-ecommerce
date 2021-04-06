package com.vdc.ecommerce.controller;

import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.dto.BranchDTO;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.service.BranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.ADMIN + ApiConstant.BRANCH)
@Api(tags = "Branch management")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @ApiOperation(value = "List all branch")
    @GetMapping(ApiConstant.LIST)
    public ResponseModel<?> getAllBranch(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageSize") Integer pageSize,
                                         @RequestParam(value = "sortBy", required = false) String field, @RequestParam(value = "isDesc", required = false) Boolean isDesc) {
        return branchService.getAll(pageNumber, pageSize, field, isDesc);
    }

    @ApiOperation(value = "Add new branch")
    @PostMapping(ApiConstant.ADD)
    public ResponseModel<?> addNewBranch(@RequestBody BranchDTO branchDTO) {
        return branchService.add(branchDTO);
    }


    @ApiOperation(value = "delete branch by Id")
    @DeleteMapping(ApiConstant.DELETE + "/{id}")
    public ResponseModel<?> deleteBranch(@PathVariable("id") Long id) {
        return branchService.deleteById(id);
    }
}
