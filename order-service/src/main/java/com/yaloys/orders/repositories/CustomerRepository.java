package com.yaloys.orders.repositories;

import com.yaloys.orders.models.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerRepository {
    private final Map<Integer, Customer> customers = new ConcurrentHashMap<>();

    public CustomerRepository()
    {
        initializeData();
    }

    private void initializeData() {
        customers.put(1, new Customer(1, "Olena", "Kovalenko", "olena.kovalenko@example.com", "+380501234567"));
        customers.put(2, new Customer(2, "Ivan", "Petrenko", "ivan.petrenko@example.com", "+380671234567"));
        customers.put(3, new Customer(3, "Mariya", "Shevchenko", "mariya.shevchenko@example.com", "+380931234567"));
    }

    public Optional<Customer> findById(Integer id) {
        return Optional.ofNullable(customers.get(id));
    }

    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    public boolean existsById(Integer id) {
        return customers.containsKey(id);
    }
}
