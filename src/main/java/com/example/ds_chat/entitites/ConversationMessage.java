package com.example.ds_chat.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "conversation_messages")
@Getter
@Setter
public class ConversationMessage {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID conversationMessageId;

    @Column(name = "sender_id")
    private UUID senderId;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "content")
    private String content;

    @Column(name = "was_seen")
    private boolean wasSeen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;
}
