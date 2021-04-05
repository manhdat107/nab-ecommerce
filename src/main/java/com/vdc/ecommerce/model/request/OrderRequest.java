package com.vdc.ecommerce.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private List<ProductOrder> productOrders;

    private String fullname;

    private String address;

    private Long phoneNumber;

    private String email;

    @Getter
    @Setter
    public static class ProductOrder {
        private Long productId;
        private int quantity;
    }

}

