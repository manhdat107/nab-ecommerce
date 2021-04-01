package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.common.ResponseMessage;
import com.vdc.ecommerce.model.Branch;
import com.vdc.ecommerce.model.Product;
import com.vdc.ecommerce.model.Quantity;
import com.vdc.ecommerce.model.dto.ProductDTO;
import com.vdc.ecommerce.model.mapper.ProductMapper;
import com.vdc.ecommerce.model.response.JsonResponse;
import com.vdc.ecommerce.reposirtory.BranchRepository;
import com.vdc.ecommerce.reposirtory.ProductRepository;
import com.vdc.ecommerce.service.ProductService;
import com.vdc.ecommerce.service.QuantityService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl extends ProductService {

    private final BranchRepository branchRepository;
    private final QuantityService quantityService;

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper, BranchRepository branchRepository,
                              QuantityService quantityService) {
        super(repository, mapper);
        this.branchRepository = branchRepository;
        this.quantityService = quantityService;
    }

    @Override
    public JsonResponse<String> addProduct(ProductDTO productDTO) {
        Long branchId = productDTO.getBranch().getId();

        Optional<Branch> branch = branchRepository.findById(branchId);
        if (!branch.isPresent()) {
            return JsonResponse.failure(ResponseMessage.NOT_FOUND.getMessage());
        }
        Product product = mapper.toEntity(productDTO);
        product.setBranch(branch.get());
        repo.save(product);

        return JsonResponse.successful(ResponseMessage.SUCCESS.getMessage());
    }

    @Override
    public JsonResponse<String> updateQuantity(Long productId, Long quantity) {
        if (productId == null) {
            return JsonResponse.failure("Product can not null");
        }
        Optional<Product> productOptional = repo.findById(productId);
        if (!productOptional.isPresent()) {
            return JsonResponse.failure(ResponseMessage.NOT_FOUND.getMessage());
        }
        Product product = productOptional.get();
        Quantity qtt;
        if (product.getQuantity() == null) {
            qtt = new Quantity();
            qtt.setProduct(product);
        } else {
            qtt = product.getQuantity();
        }
        qtt.setQuantity(quantity);
        quantityService.save(qtt);

        product.setQuantity(qtt);
        repo.save(product);
        return JsonResponse.successful(ResponseMessage.SUCCESS.getMessage());
    }

}
