package com.vdc.ecommerce.controller;

import com.vdc.ecommerce.common.ApiConstant;
import com.vdc.ecommerce.model.dto.ProductDTO;
import com.vdc.ecommerce.model.response.JsonResponse;
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
    public JsonResponse<?> getAllProduct(@RequestParam("pageNumber") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return productService.getAll(pageNum, pageSize);
    }

    @PostMapping(ApiConstant.ADD)
    public JsonResponse<?> addNewProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @PutMapping(ApiConstant.UPDATE)
    public JsonResponse<?> updateProduct(@RequestBody ProductDTO productDTO) {
        return productService.update(productDTO);
    }

    @DeleteMapping(ApiConstant.DELETE)
    public JsonResponse<?> deleteProduct(@RequestParam("id") Long productId) {
        return productService.deleteById(productId);
    }

    @PutMapping(ApiConstant.UPDATE + ApiConstant.QUANTITY)
    public JsonResponse<?> updateProductQuantity(@RequestParam Long productId, @RequestParam Long quantity) {
        return productService.updateQuantity(productId, quantity);
    }
}
