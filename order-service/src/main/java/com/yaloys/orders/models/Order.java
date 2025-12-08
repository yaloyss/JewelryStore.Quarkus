package com.yaloys.orders.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Order {
    private Integer orderId;
    private Integer customerId;
    private LocalDateTime orderDate;
    private String status;
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order ()
    {
        this.orderItems = new ArrayList<>();
    }

    public Order(Integer orderId, Integer customerId, LocalDateTime orderDate, String status, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
        this.orderItems = new ArrayList<>();
    }

    public Order(Integer orderId, Integer  customerId, LocalDateTime orderDare, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDare;
        this.status = status;

    }

    public void addOrderItem(OrderItem item)
    {
        this.orderItems.add(item);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", status='" + status + '\'' +
                ", items=" + orderItems +
                '}';
    }

}
