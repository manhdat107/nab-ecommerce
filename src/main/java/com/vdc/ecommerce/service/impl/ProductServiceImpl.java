package com.vdc.ecommerce.service.impl;

import com.querydsl.core.types.Predicate;
import com.vdc.ecommerce.common.AppUtils;
import com.vdc.ecommerce.common.PageConstant;
import com.vdc.ecommerce.common.ResponseMessage;
import com.vdc.ecommerce.model.Branch;
import com.vdc.ecommerce.model.MetricSearch;
import com.vdc.ecommerce.model.Product;
import com.vdc.ecommerce.model.Quantity;
import com.vdc.ecommerce.model.dto.ProductDTO;
import com.vdc.ecommerce.model.dto.QuantityDTO;
import com.vdc.ecommerce.model.mapper.ProductMapper;
import com.vdc.ecommerce.model.mapper.QuantityMapper;
import com.vdc.ecommerce.model.predicate.ProductPredicateService;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.model.response.ResponsePageableModel;
import com.vdc.ecommerce.reposirtory.BranchRepository;
import com.vdc.ecommerce.reposirtory.ProductRepository;
import com.vdc.ecommerce.service.ProductService;
import com.vdc.ecommerce.service.QuantityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl extends ProductService {

    private final BranchRepository branchRepository;
    private final QuantityService quantityService;
    private final ProductPredicateService productPredicateService;
    private final ProductRepository productRepository;
    private final QuantityMapper quantityMapper;

    public ProductServiceImpl(ProductRepository repository, ProductMapper productMapper, AppUtils appUtils,
                              BranchRepository branchRepository, QuantityService quantityService,
                              ProductPredicateService productPredicateService, QuantityMapper quantityMapper) {
        super(repository, productMapper, appUtils);
        this.branchRepository = branchRepository;
        this.quantityService = quantityService;
        this.productPredicateService = productPredicateService;
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

        if(productDTO.getQuantityDTO() != null) {
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
    public ResponseModel<List<ProductDTO>> findByPredicate(MetricSearch metricSearch) {
        Pageable pageable;
        int pageNum = (metricSearch == null || metricSearch.getPage() == null) ? PageConstant.PAGE_DEFAULT.getNum() : metricSearch.getPage();
        int pageSize = (metricSearch == null || metricSearch.getPageSize() == null || metricSearch.getPageSize() == 0)
                ? PageConstant.PAGE_SIZE_DEFAULT.getNum() : metricSearch.getPageSize();

        if (metricSearch == null) {
            return getAll(pageNum, pageSize, null, false);
        } else {

            if (metricSearch.getField() == null || metricSearch.getField().isEmpty()) {
                pageable = PageRequest.of(pageNum, pageSize);
            } else {
                pageable = appUtils.getPageable(pageNum, pageSize, metricSearch.getField(), metricSearch.isDest());
            }

            Predicate predicate = productPredicateService.findByMetricFilter(metricSearch);
            Page<Product> pProduct = productRepository.findAll(predicate, pageable);

            List<ProductDTO> productDTOS = mapper.toDTOs(pProduct.getContent());
            ResponsePageableModel<ProductDTO> dResponsePageableModel = new ResponsePageableModel<ProductDTO>(productDTOS, pProduct.getPageable(), pProduct.getTotalElements());
            return ResponseModel.successful(ResponseMessage.SUCCESS.getMessage(), dResponsePageableModel);
        }
    }

    @Override
    public List<Product> findByIdIn(List<Long> ids) {

        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        Predicate predicate = productPredicateService.findByProductIdIn(ids);
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
