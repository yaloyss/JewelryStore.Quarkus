package com.yaloys.user.models;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Discussion {
    private Integer discussionId;
    private Integer reviewId;
    private List<Message> messages;
}
