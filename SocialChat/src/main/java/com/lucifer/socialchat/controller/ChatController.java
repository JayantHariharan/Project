package com.lucifer.socialchat.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.lucifer.socialchat.model.ChatMessage;
import com.lucifer.socialchat.model.InfoType;
import com.lucifer.socialchat.service.UsersRecordService;

@Controller
public class ChatController {

    @Autowired
    UsersRecordService usersRecordService;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String sender = chatMessage.getSender();
        String email = chatMessage.getEmail();
        if (usersRecordService.isUserAllowedToJoin(sender, email)) {
            headerAccessor.getSessionAttributes().put("username", sender); 
            return chatMessage;
        }else {
            // User is not allowed to join, send an error message
        	System.out.println("user not allowed");
            ChatMessage errorMessage = ChatMessage.builder()
                    .type(InfoType.ERROR)
                    .content("User already joined.Check your credentials.")
                    .build();
            return errorMessage;
        } 
    }

    @GetMapping("/checkEmailAndLeaveTime")
    public ResponseEntity<Object> checkEmailAndLeaveTime(@RequestParam String email) {
        try {
        	System.out.println("chcek email");
            boolean emailExists = usersRecordService.doesEmailExist(email);
            boolean leaveTimeIsNull = usersRecordService.isLeaveTimeNull(email);

            // You can customize the response format as needed
            return ResponseEntity.ok(Map.of("exists", emailExists, "leaveTimeIsNull", leaveTimeIsNull));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error checking email and leave time");
        }
    }



}

