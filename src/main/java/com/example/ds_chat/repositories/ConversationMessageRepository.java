package com.example.ds_chat.repositories;

import com.example.ds_chat.entitites.ConversationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConversationMessageRepository extends JpaRepository <ConversationMessage, UUID> {
}
