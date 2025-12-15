package com.yaloys.reviews.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "discussions")
public class Discussion extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discussion_id")
    private Integer discussionId;

    @Column(name = "review_id",nullable = false)
    private Integer reviewId;

    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    public Discussion () {}

    public static Discussion findByDiscussionId(Integer discussionId) {
        return find("discussionId", discussionId).firstResult();
    }
    public static Discussion findByReviewId(Integer reviewId) {
        return find("reviewId", reviewId).firstResult();
    }

    @Override
    public String toString() {
        return "Discussion{" +
                "discussionId=" + discussionId +
                ", reviewId=" + reviewId +
                ", messages=" + messages +
                '}';
    }
}
