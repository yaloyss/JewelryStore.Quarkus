package com.yaloys.products.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "stones")
public class Stone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stone_id")
    private Integer stoneId;

    @Column(length = 100)
    private String name;

    public Stone (String name) {
        this.name = name;
    }

    public Stone() {}
//
//    public Integer getStoneId() { return stoneId; }
//    public void setStoneId(Integer stoneId) { this.stoneId = stoneId; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
}
