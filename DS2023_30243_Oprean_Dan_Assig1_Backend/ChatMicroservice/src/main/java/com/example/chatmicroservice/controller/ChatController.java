package com.example.chatmicroservice.controller;

import com.example.chatmicroservice.dto.ChatMessage;
import com.example.chatmicroservice.dto.MessageType;
import com.example.chatmicroservice.dto.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class ChatController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chatSendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }

    @MessageMapping("/notifyUser")
    public void sendNotificationToUser(@Payload Notification notification, SimpMessageHeaderAccessor headerAccessor) {
        String recipient = notification.getRecipient();
        String sender = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");

        if (recipient != null) {
            messagingTemplate.convertAndSend("/topic/private/notification/" + recipient, notification);
        } else {
            // Dacă destinatarul nu este specificat, trimiteți notificarea înapoi la expeditor (utilizatorul curent)
            messagingTemplate.convertAndSendToUser(sender, "/queue/notification", notification);
        }
    }

    @MessageMapping("/notifyAdmin")
    public void sendNotificationToAdmins(@Payload Notification notification) {
        messagingTemplate.convertAndSend("/topic/public/notification", notification);
    }

    @MessageMapping("/chatAddUser")
    public void addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        System.out.println("User:" + chatMessage.getSender() + " UserId:" + chatMessage.getContent());

        ChatMessage joinMessage = ChatMessage.builder()
                .content("joined the chat")
                .sender(chatMessage.getSender())
                .type(MessageType.JOIN)
                .build();

        messagingTemplate.convertAndSend("/topic/public", joinMessage);
    }
}
