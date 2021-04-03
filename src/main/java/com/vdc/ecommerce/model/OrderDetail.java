package com.vdc.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class OrderDetail extends BaseEntity<Long> {


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "order_if"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    private BigDecimal totalPrice;

    @NotNull(message = "Name can not null")
    private String fullname;

    @NotNull(message = "Address can not null")
    private String address;

    @NotNull(message = "phone number can not null")
    private Long phoneNumber;

    @Email(message = "Email Invalid, please try again.")
    private String email;

}
