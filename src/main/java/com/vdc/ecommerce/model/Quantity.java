package com.vdc.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Quantity extends BaseEntity<Long> {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(columnDefinition = "BIGINT(20) default 0")
    private Long quantity;

}
