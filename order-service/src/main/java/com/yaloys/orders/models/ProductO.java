package com.yaloys.orders.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "products")
public class ProductO {

    @Id
    @Column(name = "product_id")
    private Integer productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    public ProductO() {}
}
