package com.elephant.websocket.dto;

import lombok.Data;

@Data
public class ChatMessage {
    private String userId;
    private String username;
    private String content;
    private String avatarUrl;
    private String timestamp;
    private Boolean isSelf;
    private String id;

}
