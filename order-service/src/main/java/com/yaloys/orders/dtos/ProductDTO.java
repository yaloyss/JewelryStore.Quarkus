package com.yaloys.orders.dtos;

import com.yaloys.products.grpc.product.ProductResponse;

public class ProductDTO {
    public Integer Id;
    public String name;
    public String price;

    public ProductDTO(ProductResponse productResponse)
    {
        this.Id = productResponse.getId();
        this.name = productResponse.getName();
        this.price = productResponse.getPrice();
    }

    public ProductDTO(Integer productId, String name, String string) {
        this.Id = productId;
        this.name = name;
        this.price = string;
    }
}
