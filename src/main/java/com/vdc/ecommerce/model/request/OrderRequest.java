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

    @NotNull(message = "Name can not null")
    private String fullname;

    @NotNull(message = "Address can not null")
    private String address;

    @NotNull(message = "Phone number can not null.")
    private Long phoneNumber;

    @NotNull
    @Email(message = "Email Invalid, please try again.")
    private String email;

    @Getter
    @Setter
    public static class ProductOrder {
        private Long productId;
        private int quantity;
    }

}

