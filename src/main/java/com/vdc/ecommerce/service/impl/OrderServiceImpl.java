package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.common.AppUtils;
import com.vdc.ecommerce.common.ResponseMessage;
import com.vdc.ecommerce.model.OrderDetail;
import com.vdc.ecommerce.model.Product;
import com.vdc.ecommerce.model.mapper.OrderMapper;
import com.vdc.ecommerce.model.request.OrderRequest;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.reposirtory.OrderRepository;
import com.vdc.ecommerce.service.OrderService;
import com.vdc.ecommerce.service.ProductService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends OrderService {

    private final ProductService productService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository repo, OrderMapper mapper, AppUtils appUtils,
                            ProductService productService) {
        super(repo, mapper, appUtils);
        this.productService = productService;
        this.orderRepository = repo;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ResponseModel<String> order(OrderRequest orderRequest) throws Exception {

        if (orderRequest == null || orderRequest.getProductOrders().isEmpty()) {
            return ResponseModel.failure("Please select product");
        }
        List<OrderRequest.ProductOrder> productOrders = orderRequest.getProductOrders();

        boolean isValidOrderDetail = validOrderDetail(productOrders);
        if(!isValidOrderDetail) {
            return ResponseModel.failure("Order detail is not valid");
        }
        List<Long> productsId = productOrders.stream().map(OrderRequest.ProductOrder::getProductId).collect(Collectors.toList());

        if (productsId.isEmpty()) {
            return ResponseModel.failure("Please select product");
        }

        List<Product> products = productService.findByIds(productsId);
        List<Long> productOutOfStock = checkProductStock(products, productOrders);

        if (!productOutOfStock.isEmpty()) {
            String productOutOfStockStr = productOutOfStock.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("-", "{", "}"));
            return ResponseModel.failure("Product is out of stock: " + productOutOfStockStr);
        }
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal quantity;
        OrderRequest.ProductOrder productOrder;
        for (Product p : products) {
            BigDecimal pPrice = p.getPrice();
            Optional<OrderRequest.ProductOrder> orderOption = productOrders.stream().filter(x -> x.getProductId().equals(p.getId())).findFirst();
            if (orderOption.isPresent()) {
                productOrder = orderOption.get();
                quantity = new BigDecimal(productOrder.getQuantity());
                totalPrice = totalPrice.add(pPrice.multiply(quantity));

                // update product quantity
                p.getQuantity().setQuantity(p.getQuantity().getQuantity() - (long) productOrder.getQuantity());
            }
        }

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setTotalPrice(totalPrice);
        orderDetail.setAddress(orderRequest.getAddress());
        orderDetail.setFullname(orderRequest.getFullname());
        orderDetail.setPhoneNumber(orderRequest.getPhoneNumber());
        orderDetail.setEmail(orderRequest.getEmail());
        orderDetail.setProducts(products);
        OrderDetail success = orderRepository.save(orderDetail);

        if (success.getId() == null) {
            throw new Exception(ResponseMessage.ERROR_SYSTEM.getMessage());
        } else {
            // save for update quantity
            productService.updateList(products);
        }

        return ResponseModel.successful(ResponseMessage.SUCCESS.getMessage());
    }

    private List<Long> checkProductStock(List<Product> products, List<OrderRequest.ProductOrder> productOrders) {
        List<Long> productOutOfStock = new ArrayList<>();
        for (Product p : products) {
            productOrders.stream().filter(x -> x.getProductId().equals(p.getId())).findFirst().ifPresent(x -> {
                if (p.getQuantity() == null || p.getQuantity().getQuantity() == null || p.getQuantity().getQuantity() < x.getQuantity()) {
                    productOutOfStock.add(p.getId());
                }
            });
        }
        return productOutOfStock;
    }

    private boolean validOrderDetail(List<OrderRequest.ProductOrder> productOrder) {
        if(productOrder.isEmpty()) {
            return false;
        }
        for(OrderRequest.ProductOrder po: productOrder) {
            if(po.getProductId() == null || po.getQuantity() <= 0) {
                return false;
            }
        }
        return true;
    }


}
