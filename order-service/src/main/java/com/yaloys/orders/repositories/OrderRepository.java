//package com.yaloys.orders.repositories;
//
//import com.yaloys.orders.models.Order;
//import com.yaloys.orders.models.OrderItem;
//import jakarta.enterprise.context.ApplicationScoped;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;
//
//@ApplicationScoped
//public class OrderRepository {
//    private final Map<Integer, Order> orders = new ConcurrentHashMap<>();
//    private Integer orderIdCounter = 3;
//
//    public OrderRepository()
//    {
//        initializeData();
//    }
//
//    private void initializeData() {
//        Order order1 = new Order(1, 1, LocalDateTime.now().minusDays(5), "pending");
//        order1.addOrderItem(new OrderItem(1, 1, 1, 1, new BigDecimal("1250.00")));
//        orders.put(1, order1);
//
//        Order order2 = new Order(2, 2, LocalDateTime.now().minusDays(2), "delivered");
//        order2.addOrderItem(new OrderItem(2, 2, 2, 1, new BigDecimal("450.00")));
//        order2.addOrderItem(new OrderItem(3, 2, 3, 1, new BigDecimal("680.00")));
//        orders.put(2, order2);
//    }
//
//    public List<Order> findAll() {
//        return new ArrayList<>(orders.values());
//    }
//
//    public Optional<Order> findById(Integer id) {
//        return Optional.ofNullable(orders.get(id));
//    }
//
//    public List<Order> findByCustomerId(Integer customerId) {
//        return orders.values().stream().filter(order -> order.getCustomerId().equals(customerId))
//                .collect(Collectors.toList());
//    }
//
//    public Order save(Order order) {
//        if (order.getOrderId() == null)
//        {
//            order.setOrderId(orderIdCounter++);
//        }
//        orders.put(order.getOrderId(), order);
//        return order;
//    }
//
//    public void delete(Integer id) {
//        orders.remove(id);
//    }
//
//    public boolean existsById(Integer id) {
//        return orders.containsKey(id);
//    }
//}
package com.yaloys.orders.repositories;

import com.yaloys.orders.models.Order;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public List<Order> findByCustomerId(Integer customerId) {
        return list("customerId", customerId);
    }

    public List<Order> findByStatus(String status) {
        return list("status", status);
    }
}