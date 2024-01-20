package com.example.ds_chat.services;

import com.example.ds_chat.entitites.Conversation;
import com.example.ds_chat.entitites.ConversationMessage;
import com.example.ds_chat.entitites.ConversationUser;
import com.example.ds_chat.entitites.User;
import com.example.ds_chat.repositories.ConversationMessageRepository;
import com.example.ds_chat.repositories.ConversationRepository;
import com.example.ds_chat.repositories.ConversationUserRepository;
import com.example.ds_chat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessagingService {
    private UserRepository userRepository;
    private ConversationRepository conversationRepository;
    private ConversationUserRepository conversationUserRepository;
    private ConversationMessageRepository conversationMessageRepository;
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessagingService(UserRepository userRepository,
                            ConversationRepository conversationRepository,
                            ConversationUserRepository conversationUserRepository,
                            ConversationMessageRepository conversationMessageRepository,
                            SimpMessagingTemplate messagingTemplate) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.conversationUserRepository = conversationUserRepository;
        this.conversationMessageRepository = conversationMessageRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessage(String message, UUID senderId, UUID recipientId) {
        User sender;
        User recipient = new User();
        if (userRepository.findById(senderId).isEmpty()) {
            User user = new User();
            user.setUserId(senderId);
//            user.setUsername();
            sender = userRepository.save(user);
        } else {
            sender = userRepository.findById(senderId).get();
        }
        if (userRepository.findById(recipientId).isEmpty()) {
            User user = new User();
            user.setUserId(recipientId);
            recipient = userRepository.save(user);
        } else {
            recipient = userRepository.findById(recipientId).get();
        }

        List<User> userList = new ArrayList<>();
        userList.add(sender);
        userList.add(recipient);

        Set<UUID> conversationSet = getConversationBetweenUsers(userList);
        Conversation conversation;
        if (conversationSet.isEmpty()) {
            conversation = new Conversation();
            conversation.setCreationDate(LocalDateTime.now());
            conversationRepository.save(conversation);
            userList.forEach(user -> createAndSaveConversationUser(conversation, user));
        } else {
            Optional<Conversation> optionalConversation = conversationRepository.findById(conversationSet.stream().collect(Collectors.toList()).get(0));
            if (optionalConversation.isEmpty()) {
                return; //
            }
            conversation = optionalConversation.get();
        }

        ConversationMessage conversationMessage = new ConversationMessage();
        conversationMessage.setSenderId(senderId);
        conversationMessage.setSentAt(LocalDateTime.now());
        conversationMessage.setContent(message);
        conversationMessage.setConversation(conversation);
        conversationMessageRepository.save(conversationMessage);
        conversation.getMessages().add(conversationMessage);
        conversationRepository.save(conversation);

        for (User user : userList) {
            if (!user.equals(sender)) {
                messagingTemplate.convertAndSend("/topic/messages/" + recipient.getUserId(), message);
            }
        }
    }

    private void createAndSaveConversationUser(Conversation conversation, User user) {
        ConversationUser conversationUser = new ConversationUser();
        conversationUser.setUser(user);
        conversationUser.setConversation(conversation);
        conversationUserRepository.save(conversationUser);
    }

    private boolean doesConversationBetweenUsersExist(List<User> users) {
        if (users == null || users.isEmpty()) {
            return false;
        }

        Set<UUID> commonConversations = new HashSet<>(getConversationIdsForUser(users.get(0)));

        for (int i = 1; i < users.size(); i++) {
            commonConversations.retainAll(getConversationIdsForUser(users.get(i)));
        }

        return !commonConversations.isEmpty();
    }

    private Set<UUID> getConversationBetweenUsers(List<User> users) {
        if (users == null || users.isEmpty()) {
            return null;
        }

        Set<UUID> commonConversations = new HashSet<>(getConversationIdsForUser(users.get(0)));

        for (int i = 1; i < users.size(); i++) {
            commonConversations.retainAll(getConversationIdsForUser(users.get(i)));
        }

        return commonConversations;
    }

    private Set<UUID> getConversationIdsForUser(User user) {
        Optional<List<ConversationUser>> optionalConversationUser = conversationUserRepository.findAllByUser(user);
        if (optionalConversationUser.isEmpty()) {
            return new HashSet<UUID>();
        }
        return optionalConversationUser.get().stream()
                .map(ConversationUser::getConversation)
                .map(Conversation::getConversationId)
                .collect(Collectors.toSet());
    }
}
