package com.yaloys.user.models;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Review {
    private Integer reviewId;
    private Integer productId;
    private Integer rating;
    private String title;
    private String body;
    private LocalDateTime createdAt;

    public int getEmptyStars() {
        return 5 - rating;
    }

}
