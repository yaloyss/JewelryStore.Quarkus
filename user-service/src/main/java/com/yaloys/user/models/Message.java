package com.yaloys.user.models;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Message {
    private Integer messageId;
    private String content;
    private LocalDateTime createdAt;
}
