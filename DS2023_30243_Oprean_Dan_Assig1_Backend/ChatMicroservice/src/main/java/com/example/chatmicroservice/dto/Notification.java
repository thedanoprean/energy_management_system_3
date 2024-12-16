package com.example.chatmicroservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    private String sender;
    private String recipient;
    private NotificationType type;
}
