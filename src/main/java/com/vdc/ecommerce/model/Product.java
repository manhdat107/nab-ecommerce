package com.vdc.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Product extends CommonEntity<Long> {

    private String name;

    @Enumerated(EnumType.STRING)
    private Color color;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    @Enumerated(EnumType.STRING)
    private Branch branch;
}
