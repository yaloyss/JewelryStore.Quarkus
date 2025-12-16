package com.yaloys.orders.repositories;

import com.yaloys.orders.models.ProductO;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<ProductO> {

    public List<ProductO> findByCategoryId(Integer categoryId) {
        return list("categoryId", categoryId);
    }

    public List<ProductO> findByManufacturer(String manufacturer) {
        return list("manufacturer", manufacturer);
    }
//    private final Map<Integer, ProductO> products = new ConcurrentHashMap<>();
//
//    public ProductRepository()
//    {
//        initializeData();
//    }
//
//    private void initializeData() {
//        products.put(1, new ProductO(1, "Gold Ring 18K", new BigDecimal("1250.00")));
//        products.put(2, new ProductO(2, "Silver Earrings", new BigDecimal("450.00")));
//        products.put(3, new ProductO(3, "Diamond Necklace", new BigDecimal("3200.00")));
//        products.put(4, new ProductO(4, "Platinum Bracelet", new BigDecimal("2100.00")));
//        products.put(5, new ProductO(5, "Pearl Pendant", new BigDecimal("680.00")));
//    }
//
//    public Optional<ProductO> findById(Integer id) {
//        return Optional.ofNullable(products.get(id));
//    }
//
//    public List<ProductO> findAll() {
//        return new ArrayList<>(products.values());
//    }
//
//    public boolean existsById(Integer id) {
//        return products.containsKey(id);
//    }
}
