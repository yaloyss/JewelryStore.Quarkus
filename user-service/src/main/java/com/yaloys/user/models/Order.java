package com.yaloys.user.models;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Order {
    private Integer orderId;
    private Integer customerId;
    private LocalDateTime orderDate;
    private String status;
    private List<OrderItem> orderItems;
}
