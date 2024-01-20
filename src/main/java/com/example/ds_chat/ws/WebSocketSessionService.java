package com.example.ds_chat.ws;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketSessionService {
    private final Map<String, String> sessionUserMap = new ConcurrentHashMap<>();

    public void registerSession(String sessionId, String username) {
        sessionUserMap.put(sessionId, username);
        System.out.println(sessionUserMap);
    }

    public void removeSession(String sessionId) {
        sessionUserMap.remove(sessionId);
        System.out.println(sessionUserMap);
    }

    public String getUsername(String sessionId) {
        return sessionUserMap.get(sessionId);
    }

    public boolean isUserActive(String userId) {
        return sessionUserMap.values().contains(userId);
    }
}

