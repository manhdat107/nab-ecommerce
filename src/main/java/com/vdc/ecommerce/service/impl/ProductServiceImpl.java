package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.model.Branch;
import com.vdc.ecommerce.model.Product;
import com.vdc.ecommerce.model.dto.ProductDTO;
import com.vdc.ecommerce.model.mapper.ProductMapper;
import com.vdc.ecommerce.model.response.JsonResponse;
import com.vdc.ecommerce.reposirtory.BranchRepository;
import com.vdc.ecommerce.reposirtory.ProductRepository;
import com.vdc.ecommerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl extends ProductService {

    private final BranchRepository branchRepository;

    public ProductServiceImpl(ProductRepository repository, ProductMapper mapper, BranchRepository branchRepository) {
        super(repository, mapper);
        this.branchRepository = branchRepository;
    }

    @Override
    public JsonResponse<String> addProduct(ProductDTO productDTO) {
        Long branchId = productDTO.getBranch().getId();

        Optional<Branch> branch = branchRepository.findById(branchId);
        if (!branch.isPresent()) {
            return JsonResponse.failure("Branch not found");
        }
        Product product = mapper.toEntity(productDTO);
        product.setBranch(branch.get());
        repo.save(product);

        return JsonResponse.successful("Add new Product success!");
    }

}
