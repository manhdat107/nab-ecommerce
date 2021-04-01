package com.vdc.ecommerce.controller;

import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.dto.ProductDTO;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.PRODUCT)
@Api(tags = "Product Management")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(ApiConstant.LIST)
    public ResponseModel<?> getAllProduct(@RequestParam("pageNumber") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam(value = "sortBy", required = false) String field, @RequestParam(value = "isDesc", required = false) boolean isDesc) {
        return productService.getAll(pageNum, pageSize, field, isDesc);
    }

    @PostMapping(ApiConstant.ADD)
    public ResponseModel<?> addNewProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @PutMapping(ApiConstant.UPDATE)
    public ResponseModel<?> updateProduct(@RequestBody ProductDTO productDTO) {
        return productService.update(productDTO);
    }

    @DeleteMapping(ApiConstant.DELETE)
    public ResponseModel<?> deleteProduct(@RequestParam("id") Long productId) {
        return productService.deleteById(productId);
    }

    @PutMapping(ApiConstant.UPDATE + ApiConstant.QUANTITY)
    public ResponseModel<?> updateProductQuantity(@RequestParam Long productId, @RequestParam Long quantity) {
        return productService.updateQuantity(productId, quantity);
    }
}
