package com.vdc.ecommerce.controller;

import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.dto.ProductDTO;
import com.vdc.ecommerce.model.response.JsonResponseEntity;
import com.vdc.ecommerce.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.ADMIN)
@Api(tags = "Product Management")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(ApiConstant.LIST)
    public JsonResponseEntity<?> getAllProduct(@RequestParam("pageNumber") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return productService.getAll(pageNum, pageSize);
    }

    @PostMapping(ApiConstant.ADD)
    public JsonResponseEntity<ProductDTO> addNewProduct(@RequestBody ProductDTO productDTO) {
        return productService.add(productDTO);
    }


}
