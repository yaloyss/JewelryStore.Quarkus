package com.yaloys.products.models;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class Product {
    private Integer productId;
    private String name;
    private BigDecimal price;
    private BigDecimal weight;
    private BigDecimal size;
    private String manufacturer;
    private Integer metalId;
    private Integer categoryId;
    private List<Integer> stoneIds;

    public Product () {}

    public Product (Integer productId, String name, BigDecimal price, BigDecimal weight, BigDecimal size, String manufacturer, Integer metalId, Integer categoryId)
    {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.size = size;
        this.manufacturer = manufacturer;
        this.metalId = metalId;
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", weight=" + weight +
                ", size=" + size +
                ", manufacturer=" + manufacturer +
                ", metalId=" + metalId +
                ", stoneIds=" + stoneIds +
                '}';
    }

}
