package com.lucifer.socialchat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.lucifer.socialchat.model.ChatMessage;
import com.lucifer.socialchat.model.InfoType;
import com.lucifer.socialchat.service.UsersRecordService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
	
	private final SimpMessageSendingOperations messageTemplate;
	
	@Autowired
    private UsersRecordService usersRecordService;

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
	    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
	    String username = (String) headerAccessor.getSessionAttributes().get("username");

	    if (username != null) {
	        usersRecordService.updateLeaveTime(username);
	        log.info("User Disconnected: {}", username);

	        // Notify chat about user leaving
	        var leaveMessage = ChatMessage.builder()
	                .type(InfoType.LEAVE)
	                .sender(username)
	                .build();
	        messageTemplate.convertAndSend("/topic/public", leaveMessage);
	    }
	}

}
