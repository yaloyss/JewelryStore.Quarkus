package com.yaloys.reviews.repositories;
import com.yaloys.reviews.models.Review;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReviewRepository {
    private final Map<Integer, Review> reviews = new ConcurrentHashMap<>();
    private Integer reviewIdCounter = 6;

    public ReviewRepository() {
        initializeData();
    }

    private void initializeData() {
        reviews.put(1, new Review(1, 1, 5, "Perfect engagement ring!",
        "Absolutely stunning diamond ring. The quality is exceptional and my fiancée loves it!",
        LocalDateTime.now().minusDays(10)));

        reviews.put(2, new Review(2, 1, 4, "Beautiful but expensive",
        "The ring is gorgeous and well-crafted. Worth the price but it's quite an investment.",
        LocalDateTime.now().minusDays(5)));

        reviews.put(3, new Review(3, 2, 5, "Luxurious piece",
        "This Cartier necklace is absolutely breathtaking. The ruby is vibrant and the gold work is exquisite.",
        LocalDateTime.now().minusDays(8)));

        reviews.put(4, new Review(4, 2, 5, "Stunning craftsmanship",
        "Every detail is perfect. The necklace feels luxurious and looks even better in person.",
        LocalDateTime.now().minusDays(3)));

        reviews.put(5, new Review(5, 3, 4, "Elegant earrings",
        "Beautiful sapphire and diamond combination. The white gold setting is very elegant.",
        LocalDateTime.now().minusDays(6)));
    }

    public List<Review> findAll() {
        return new ArrayList<>(reviews.values());
    }

    public Optional<Review> findById(Integer id) {
        return Optional.ofNullable(reviews.get(id));
    }

    public List<Review> findByProductId(Integer productId) {
        return reviews.values().stream()
                .filter(review -> review.getProductId().equals(productId))
                .collect(Collectors.toList());
    }

    public Review save(Review review) {
        if (review.getReviewId() == null) {
            review.setReviewId(reviewIdCounter++);
        }
        if (review.getCreatedAt() == null) {
            review.setCreatedAt(LocalDateTime.now());
        }
        reviews.put(review.getReviewId(), review);
        return review;
    }

    public void deleteById(Integer id) {
        reviews.remove(id);
    }
}
