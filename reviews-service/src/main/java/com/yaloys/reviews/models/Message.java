package com.yaloys.reviews.models;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Message {
    private Integer messageId;
    private Integer discussionId;
    private String content;
    private LocalDateTime createdAt;

    public Message() {}

    public Message(Integer messageId, Integer discussionId, String content, LocalDateTime createdAt) {
        this.messageId = messageId;
        this.discussionId = discussionId;
        this.content = content;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", discussionId=" + discussionId +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
