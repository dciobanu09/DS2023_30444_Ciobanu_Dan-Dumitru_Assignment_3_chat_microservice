package com.example.ds_chat.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "conversation_users")
@Getter
@Setter
public class ConversationUser {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID conversationUserId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;
}
