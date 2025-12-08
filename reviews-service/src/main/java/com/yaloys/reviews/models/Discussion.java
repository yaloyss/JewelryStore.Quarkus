package com.yaloys.reviews.models;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Discussion {
    private Integer discussionId;
    private Integer reviewId;
    private List<Message> messages;

    public Discussion() {
        this.messages = new ArrayList<>();
    }

    public Discussion(Integer discussionId, Integer reviewId) {
        this.discussionId = discussionId;
        this.reviewId = reviewId;
        this.messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        this.messages.add(message);
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
