package com.yaloys.products.repositories;

import com.yaloys.products.models.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<Category> {

    public Optional<Category> findByName(String name) {
        return find("name", name).firstResultOptional();
    }
//
//    private final Map<Integer, Category> categories = new HashMap<>();
//    private int idCounter = 1;
//
//    public CategoryRepository()
//    {
//        save(new Category(null, "Rings"));
//        save(new Category(null, "Necklaces"));
//        save(new Category(null, "Earrings"));
//        save(new Category(null, "Bracelets"));
//        save(new Category(null, "Pendants"));
//    }
//
//    public List<Category> findAll() {
//        return new ArrayList<>(categories.values());
//    }
//
//    public Optional<Category> findById(Integer id) {
//        return Optional.ofNullable(categories.get(id));
//    }
//
//    public Category save(Category category)
//    {
//        if (category.getCategoryId() == null) {
//            category.setCategoryId(idCounter++);
//        }
//        categories.put(category.getCategoryId(), category);
//        return category;
//    }
//
//    public void deleteById(Integer id) {
//        categories.remove(id);
//    }
//
//    public boolean existsById(Integer id) {
//        return categories.containsKey(id);
//    }
}
