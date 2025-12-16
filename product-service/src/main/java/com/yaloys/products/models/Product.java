package com.yaloys.products.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(precision = 10, scale = 2)
    private BigDecimal size;

    @Column(length = 100)
    private String manufacturer;

    @Column(name = "metal_id")
    private Integer metalId;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "stone_id")
    private Integer stoneId;

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
                ", stoneId=" + stoneId +
                '}';
    }
}
