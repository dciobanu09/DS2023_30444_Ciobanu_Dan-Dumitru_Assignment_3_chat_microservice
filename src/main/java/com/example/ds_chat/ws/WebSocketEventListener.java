package com.example.ds_chat.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Component
public class WebSocketEventListener {

    private final WebSocketSessionService sessionService;

    @Autowired
    public WebSocketEventListener(WebSocketSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        String sessionId = SimpMessageHeaderAccessor.getSessionId(event.getMessage().getHeaders());
        System.out.println("Connect - Session ID: " + sessionId);
        Map<String, Object> attributes = SimpMessageHeaderAccessor.getSessionAttributes(event.getMessage().getHeaders());
        String userId = (String) attributes.get("userId");
        if (userId != null && sessionId != null) {
            sessionService.registerSession(sessionId, userId);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        String sessionId2 = SimpMessageHeaderAccessor.getSessionId(event.getMessage().getHeaders());

        System.out.println("Disconnect - Session ID: " + sessionId);
        System.out.println("Disconnect - Session ID2: " + sessionId2);
        sessionService.removeSession(sessionId);
    }
}

