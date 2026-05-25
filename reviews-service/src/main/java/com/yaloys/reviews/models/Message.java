package com.yaloys.reviews.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
public class Message extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;

    @ManyToOne
    @JoinColumn(name = "discussion_id")
    @JsonIgnore
    public Discussion discussion;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Message() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", discussion=" + discussion +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
