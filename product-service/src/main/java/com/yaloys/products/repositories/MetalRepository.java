package com.yaloys.products.repositories;

import com.yaloys.products.models.Metal;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
public class MetalRepository {
    private final Map<Integer, Metal> metals = new HashMap<>();
    private int idCounter = 1;

    public MetalRepository()
    {
        save(new Metal(null, "Gold", "Yellow"));
        save(new Metal(null, "Gold", "White"));
        save(new Metal(null, "Gold", "Rose"));
        save(new Metal(null, "Silver", "White"));
        save(new Metal(null, "Platinum", "White"));
    }

    public List<Metal> findAll() {
        return new ArrayList<>(metals.values());
    }

    public Optional<Metal> findById(Integer id) {
        return Optional.ofNullable(metals.get(id));
    }

    public Metal save(Metal metal)
    {
        if (metal.getMetalId() == null)
        {
            metal.setMetalId(idCounter++);
        }
        metals.put(metal.getMetalId(), metal);
        return metal;
    }

    public void deleteById(Integer id) {
        metals.remove(id);
    }

    public boolean existsById(Integer id) {
        return metals.containsKey(id);
    }

}
