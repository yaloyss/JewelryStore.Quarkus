package com.yaloys.orders.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Customer {
    private Integer customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public Customer() {}
}
