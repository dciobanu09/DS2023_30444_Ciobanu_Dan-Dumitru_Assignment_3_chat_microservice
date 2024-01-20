package com.example.ds_chat.utils;

public class ChatMessage {
    private String senderId;
    private String recipientId;
    private String content;
    private boolean areMessagesSeen;
    private boolean isTyping;

    public ChatMessage() {
    }

    public ChatMessage(String senderId, String recipientId, String content, boolean areMessagesSeen, boolean isTyping) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.areMessagesSeen = areMessagesSeen;
        this.isTyping = isTyping;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getAreMessagesSeen() {
        return areMessagesSeen;
    }

    public void setAreMessagesSeen(boolean areMessagesSeen) {
        this.areMessagesSeen = areMessagesSeen;
    }

    public boolean isTyping() {
        return isTyping;
    }

    public void setTyping(boolean isTyping) {
        this.isTyping = isTyping;
    }

    // toString method for logging and debugging
    @Override
    public String toString() {
        return "ChatMessage{" +
                "senderId='" + senderId + '\'' +
                ", recipientId='" + recipientId + '\'' +
                ", content='" + content + '\'' +
                ", areMessagesSeen=" + areMessagesSeen +
                ", isTyping=" + isTyping +
                '}';
    }
}

