package com.yaloys.reviews.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reviews")
public class Review extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @Column(nullable = false)
    public Integer rating;

    @Column(nullable = false)
    public Integer productId;

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    public String body;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Review() {
        this.createdAt = LocalDateTime.now();
    }

    public static List<Review> findByProductId(Integer productId) {
        return list("productId", productId);
    }

    public static Review findByRatingId(Integer ratingId) {
        return find("ratingId", ratingId).firstResult();
    }

    public static Review findByReviewId(Integer reviewId) {
        return findById(reviewId);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", productId=" + productId +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
