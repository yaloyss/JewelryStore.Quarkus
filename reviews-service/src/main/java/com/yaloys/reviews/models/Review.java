package com.yaloys.reviews.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @Column(nullable = false)
    public Integer rating;

    @Column(name = "product_id", nullable = false)
    public Integer productId;

    @Column(nullable = false)
    public String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    public String body;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Review() {
        this.createdAt = LocalDateTime.now();
    }

    public static List<Review> findByProductId(Integer productId) {
        return list("productId", productId);
    }

    public static Review findByRating(Integer rating) {
        return find("rating", rating).firstResult();
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
