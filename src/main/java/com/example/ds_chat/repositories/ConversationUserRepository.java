package com.example.ds_chat.repositories;

import com.example.ds_chat.entitites.ConversationUser;
import com.example.ds_chat.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationUserRepository extends JpaRepository<ConversationUser, UUID> {
    Optional<List<ConversationUser>> findAllByUser(User user);
}
