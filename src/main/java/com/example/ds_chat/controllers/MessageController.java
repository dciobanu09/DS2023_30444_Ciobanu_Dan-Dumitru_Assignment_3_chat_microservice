package com.example.ds_chat.controllers;

import com.example.ds_chat.services.MessagingService;
import com.example.ds_chat.utils.ChatMessage;
import com.example.ds_chat.ws.WebSocketSessionService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private WebSocketSessionService sessionService;
    @Autowired
    private MessagingService messagingService;

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload ChatMessage chatMessage) {
//        if (sessionService.isUserActive(chatMessage.getRecipientId())) {
        String jsonMessage = new Gson().toJson(chatMessage);
//            messagingTemplate.convertAndSend("/topic/messages/" + chatMessage.getRecipientId(), chatMessage.getContent());
        messagingTemplate.convertAndSend("/topic/messages/" + chatMessage.getRecipientId(), jsonMessage);
//        messagingService.sendMessage(chatMessage.getContent(), UUID.fromString(chatMessage.getSenderId()), UUID.fromString(chatMessage.getRecipientId()));
    }
//    }

//    @MessageMapping("/notifyTyping")
//    public void notifyTyping(@Payload ChatMessage chatMessage) {
////        if (sessionService.isUserActive(chatMessage.getRecipientId())) {
//        String jsonMessage = new Gson().toJson(chatMessage);
////            messagingTemplate.convertAndSend("/topic/messages/" + chatMessage.getRecipientId(), chatMessage.getContent());
//        messagingTemplate.convertAndSend("/topic/notifications/" + chatMessage.getRecipientId(), jsonMessage);
//        messagingService.sendMessage(chatMessage.getContent(), UUID.fromString(chatMessage.getSenderId()), UUID.fromString(chatMessage.getRecipientId()));
//    }
////    }

    @MessageMapping("/notifyTyping")
    public void notifyTyping(@Payload String rawPayload, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println("Raw: " + rawPayload);
        try {
            ChatMessage chatMessage = new Gson().fromJson(rawPayload, ChatMessage.class);
            System.out.println("Deserialized typing notification: " + chatMessage);
            messagingTemplate.convertAndSend("/topic/notifications/" + chatMessage.getRecipientId(), chatMessage);
//            messagingService.sendMessage(chatMessage.getContent(), UUID.fromString(chatMessage.getSenderId()), UUID.fromString(chatMessage.getRecipientId()));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/notifySeen")
    public void notifySeen(@Payload String rawPayload) {
        System.out.println("Raw: " + rawPayload);
        try {
            ChatMessage chatMessage = new Gson().fromJson(rawPayload, ChatMessage.class);
            System.out.println("Deserialized typing notification: " + chatMessage);
            messagingTemplate.convertAndSend("/topic/seenStatus/" + chatMessage.getRecipientId(), chatMessage);
//            messagingService.sendMessage(chatMessage.getContent(), UUID.fromString(chatMessage.getSenderId()), UUID.fromString(chatMessage.getRecipientId()));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

//    @MessageMapping("/markMessageAsSeen")
//    public void markMessageAsSeen(@Payload ChatMessage chatMessage) {
//        if (sessionService.isUserActive(chatMessage.getRecipientId())) {
//            messagingTemplate.convertAndSend("/topic/messages/" + chatMessage.getRecipientId(), "messages are seen");
//        }
//    }
}
