package com.yaloys.products.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "metals")
public class Metal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metal_id")
    private Integer metalId;

    @Column(length = 100)
    private String name;

    @Column(length = 50)
    private String color;

    public Metal() {}


}
