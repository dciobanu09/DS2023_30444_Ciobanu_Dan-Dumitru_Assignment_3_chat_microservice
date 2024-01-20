package com.example.ds_chat.entitites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @Column
    private UUID userId;

    @Column
    private String username;

    @OneToMany(mappedBy = "user")
    private Set<ConversationUser> conversationUsers;
}
