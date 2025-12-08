package com.yaloys.reviews.repositories;

import com.yaloys.reviews.models.Discussion;
import com.yaloys.reviews.models.Message;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class DiscussionRepository {
    private final Map<Integer, Discussion> discussions = new ConcurrentHashMap<>();
    private Integer discussionIdCounter = 3;
    private Integer messageIdCounter = 5;

    public DiscussionRepository() {
        initializeData();
    }

    private void initializeData() {
        Discussion discussion1 = new Discussion(1, 1);
        discussion1.addMessage(new Message(1, 1, "Thank you for your wonderful feedback! We're thrilled your fiancée loves the ring!", LocalDateTime.now().minusDays(9)));
        discussions.put(1, discussion1);

        Discussion discussion2 = new Discussion(2, 3);
        discussion2.addMessage(new Message(2, 2, "We appreciate your kind words about our Cartier piece!", LocalDateTime.now().minusDays(7)));
        discussion2.addMessage(new Message(3, 2, "Do you have matching earrings for this necklace?", LocalDateTime.now().minusDays(6)));
        discussion2.addMessage(new Message(4, 2, "Yes! Please check our Ruby collection for matching pieces.", LocalDateTime.now().minusDays(6)));
        discussions.put(2, discussion2);
    }

    public List<Discussion> findAll() {
        return new ArrayList<>(discussions.values());
    }

    public Optional<Discussion> findById(Integer id) {
        return Optional.ofNullable(discussions.get(id));
    }

    public Optional<Discussion> findByReviewId(Integer reviewId) {
        return discussions.values().stream()
                .filter(d -> d.getReviewId().equals(reviewId))
                .findFirst();
    }

    public Discussion save(Discussion discussion) {
        if (discussion.getDiscussionId() == null) {
            discussion.setDiscussionId(discussionIdCounter++);
        }
        discussions.put(discussion.getDiscussionId(), discussion);
        return discussion;
    }

    public Message addMessage(Integer discussionId, Message message) {
        Optional<Discussion> discussionOpt = findById(discussionId);

        if (discussionOpt.isPresent()) {
            if (message.getMessageId() == null) {
                message.setMessageId(messageIdCounter++);
            }
            if (message.getCreatedAt() == null) {
                message.setCreatedAt(LocalDateTime.now());
            }
            message.setDiscussionId(discussionId);

            Discussion discussion = discussionOpt.get();
            discussion.addMessage(message);
            save(discussion);
            return message;
        }
        return null;
    }

    public void deleteById(Integer id) {
        discussions.remove(id);
    }
}
