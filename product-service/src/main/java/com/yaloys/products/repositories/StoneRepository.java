package com.yaloys.products.repositories;

import com.yaloys.products.models.Stone;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
public class StoneRepository {
    private static final Map<Integer, Stone> stones = new HashMap<>();
    private static int idCounter = 1;

    public StoneRepository()
    {
        save(new Stone(null, "Diamond"));
        save(new Stone(null, "Ruby"));
        save(new Stone(null, "Sapphire"));
        save(new Stone(null, "Pearl"));
        save(new Stone(null, "Onyx"));
    }

    public static List<Stone> findAll()
    {
        return new ArrayList<>(stones.values());
    }

    public static Optional<Stone> findById(Integer id)
    {
        return Optional.ofNullable(stones.get(id));
    }

    public static Stone save(Stone stone)
    {
        if (stone.getStoneId() == null)
        {
            stone.setStoneId(idCounter++);
        }
        stones.put(stone.getStoneId(), stone);
        return stone;
    }

    public static void deleteById(Integer id)
    {
        stones.remove(id);
    }

    public static boolean existsById(Integer id)
    {
        return stones.containsKey(id);
    }
}
