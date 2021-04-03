package com.vdc.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
public class Product extends BaseEntity<Long> {

    private String name;

    @Enumerated(EnumType.STRING)
    private Color color;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "product")
    private Quantity quantity;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    private List<OrderDetail> orderDetails;

}
