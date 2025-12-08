package com.yaloys.products.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Metal {
    private Integer metalId;
    private String name;
    private String color;

    public Metal() {}

}
