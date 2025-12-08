package com.yaloys.reviews.models;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Review {
    private Integer reviewId;
    private Integer rating;
    private Integer productId;
    private String title;
    private String body;
    private LocalDateTime createdAt;

    public Review() {}

    public Review(Integer reviewId, Integer rating, Integer productId, String title, String body, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.productId = productId;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
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
