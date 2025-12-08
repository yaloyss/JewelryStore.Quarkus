package com.yaloys.products.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stone {
    private Integer stoneId;
    private String name;

    public Stone () {}

    public Integer getStoneId() { return stoneId; }
    public void setStoneId(Integer stoneId) { this.stoneId = stoneId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
