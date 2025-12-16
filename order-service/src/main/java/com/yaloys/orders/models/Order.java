package com.yaloys.orders.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(length = 20)
    private String status;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order () {
        this.orderDate = LocalDateTime.now();
        this.status = "pending";
    }

//    public Order(Integer orderId, Integer  customerId, LocalDateTime orderDare, String status) {
//        this.orderId = orderId;
//        this.customerId = customerId;
//        this.orderDate = orderDare;
//        this.status = status;
//
//    }

    public void addOrderItem(OrderItem item)
    {
        orderItems.add(item);
        item.setOrder(this);
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
