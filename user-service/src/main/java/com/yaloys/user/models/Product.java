package com.yaloys.user.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Product {
    private Integer productId;
    private String name;
    private BigDecimal price;
    private BigDecimal weight;
    private BigDecimal size;
    private String manufacturer;
    private Integer metalId;
    private List<Integer> stoneIds;
    private Integer categoryId;

}
