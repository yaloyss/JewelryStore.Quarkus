package com.yaloys.orders.models;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductO {
    private Integer productId;
    private String name;
    private BigDecimal price;

    public ProductO() {}

    public ProductO(Integer productId, String name, BigDecimal price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }
}
