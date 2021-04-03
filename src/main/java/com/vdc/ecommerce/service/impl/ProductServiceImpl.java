package com.vdc.ecommerce.service.impl;

import com.querydsl.core.types.Predicate;
import com.vdc.ecommerce.common.ResponseMessage;
import com.vdc.ecommerce.model.Branch;
import com.vdc.ecommerce.model.Product;
import com.vdc.ecommerce.model.Quantity;
import com.vdc.ecommerce.model.dto.ProductDTO;
import com.vdc.ecommerce.model.dto.QuantityDTO;
import com.vdc.ecommerce.model.mapper.ProductMapper;
import com.vdc.ecommerce.model.mapper.QuantityMapper;
import com.vdc.ecommerce.model.predicate.ProductPredicate;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.reposirtory.BranchRepository;
import com.vdc.ecommerce.reposirtory.ProductRepository;
import com.vdc.ecommerce.service.ProductService;
import com.vdc.ecommerce.service.QuantityService;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl extends ProductService {

    private final BranchRepository branchRepository;
    private final QuantityService quantityService;
    private final ProductPredicate productPredicate;
    private final ProductRepository productRepository;
    private final QuantityMapper quantityMapper;

    public ProductServiceImpl(ProductRepository repository, ProductMapper productMapper, QuerydslPredicateExecutor<Product> queryDsl,
                              BranchRepository branchRepository, QuantityService quantityService,
                              ProductPredicate productPredicate, QuantityMapper quantityMapper) {
        super(repository, productMapper, queryDsl);
        this.branchRepository = branchRepository;
        this.quantityService = quantityService;
        this.productPredicate = productPredicate;
        this.productRepository = repository;
        this.quantityMapper = quantityMapper;
    }

    @Override
    public ResponseModel<String> addProduct(ProductDTO productDTO) {
        Long branchId = productDTO.getBranch().getId();

        Optional<Branch> branch = branchRepository.findById(branchId);
        if (!branch.isPresent()) {
            return ResponseModel.failure(ResponseMessage.NOT_FOUND.getMessage());
        }
        Product product = mapper.toEntity(productDTO);
        product.setBranch(branch.get());

        if (productDTO.getQuantityDTO() != null) {
            QuantityDTO quantityDTO = new QuantityDTO();
            quantityDTO.setQuantity(productDTO.getQuantityDTO().getQuantity());

            Quantity quantity = quantityMapper.toEntity(quantityDTO);
            product.setQuantity(quantity);
            quantity.setProduct(product);
        }
        productRepository.save(product);

        return ResponseModel.successful(ResponseMessage.SUCCESS.getMessage());
    }

    @Override
    public ResponseModel<String> updateQuantity(Long productId, Long quantity) {
        if (productId == null) {
            return ResponseModel.failure("Product can not null");
        }
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            return ResponseModel.failure(ResponseMessage.NOT_FOUND.getMessage());
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
        productRepository.save(product);
        return ResponseModel.successful(ResponseMessage.SUCCESS.getMessage());
    }

    @Override
    public List<Product> findByIds(List<Long> ids) {

        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        Predicate predicate = productPredicate.findByProductIdIn(ids);
        return (List<Product>) productRepository.findAll(predicate);
    }

    @Override
    public void updateList(List<Product> products) {
        if (CollectionUtils.isEmpty(products)) {
            return;
        }
        productRepository.saveAll(products);
    }

}
