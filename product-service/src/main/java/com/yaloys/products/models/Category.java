package com.yaloys.products.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(length = 100)
    private String name;

    public Category () {}

    public Category(String name) {
        this.name = name;
    }
//    public Integer getCategoryId() { return categoryId; }
//    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
}
