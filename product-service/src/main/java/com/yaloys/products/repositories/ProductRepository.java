package com.yaloys.products.repositories;

import com.yaloys.products.models.Product;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.*;

@ApplicationScoped
public class ProductRepository {
    private final Map<Integer, Product> products = new HashMap<>();
    private int idCounter = 1;
    private final Map<Integer, List<Integer>> productStones = new HashMap<>();

    public ProductRepository() {
        Product ring1 = new Product(null, "Diamond Engagement Ring", new BigDecimal("2500.00"),
        new BigDecimal("3.5"), new BigDecimal("16.5"), "Tiffany & Co", 1, 1);
        ring1 = save(ring1);
        addStoneToProduct(ring1.getProductId(), 1);

        Product necklace1 = new Product(null, "Ruby Gold Necklace",
        new BigDecimal("1800.00"), new BigDecimal("12.3"), new BigDecimal("45.0"), "Cartier", 1, 2);
        necklace1 = save(necklace1);
        addStoneToProduct(necklace1.getProductId(), 2);

        Product earrings1 = new Product(null, "Sapphire White Gold Earrings",
        new BigDecimal("950.00"), new BigDecimal("4.2"), new BigDecimal("8.0"), "Bulgari", 2, 3);
        earrings1 = save(earrings1);
        addStoneToProduct(earrings1.getProductId(), 3);
        addStoneToProduct(earrings1.getProductId(), 1);
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        for (Product p : this.products.values()) {
            loadStones(p);
            products.add(p);
        }
        return products;
    }

    public Product findById(Integer id) {
        Product product = products.get(id);
        if (product != null)
        {
            loadStones(product);
        }
        return product;
    }

    public Product save(Product product) {
        if (product.getProductId() == null)
        {
            product.setProductId(idCounter++);
        }
        products.put(product.getProductId(), product);

        if (product.getStoneIds() != null)
        {
            productStones.put(product.getProductId(), new ArrayList<>(product.getStoneIds()));
        }

        return product;
    }

    public void deleteById(Integer id) {
        products.remove(id);
        productStones.remove(id);
    }

    public boolean existsById(Integer id) {
        return products.containsKey(id);
    }

    public void addStoneToProduct(Integer productId, Integer stoneId) {
        List<Integer> stones = productStones.get(productId);
        if (stones == null) {
            stones = new ArrayList<>();
            productStones.put(productId, stones);
        }
        stones.add(stoneId);
    }

    public void removeStoneFromProduct(Integer productId, Integer stoneId) {
        List<Integer> stones = productStones.get(productId);
        if (stones != null)
        {
            stones.remove(stoneId);
        }
    }

    public List<Integer> getProductStones(Integer productId) {
        return productStones.getOrDefault(productId, new ArrayList<>());
    }

    public List<Product> findByCategory(Integer categoryId) {
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getCategoryId().equals(categoryId)) {
                loadStones(p);
                result.add(p);
            }
        }
        return result;
    }

    private void loadStones(Product product) {
        product.setStoneIds(getProductStones(product.getProductId()));
    }
}


