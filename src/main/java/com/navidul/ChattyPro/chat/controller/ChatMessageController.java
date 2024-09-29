package com.navidul.ChattyPro.chat.controller;

import com.navidul.ChattyPro.chat.entity.ChatMessage;
import com.navidul.ChattyPro.chat.entity.ChatNotification;
import com.navidul.ChattyPro.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final SimpMessagingTemplate messagingTemplate;

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        ChatMessage savedChatMessage = chatMessageService.save(chatMessage);
    messagingTemplate.convertAndSendToUser(savedChatMessage.getReceiverId(),"/queue/messages", ChatNotification.builder().id(savedChatMessage.getId()).senderId(savedChatMessage.getSenderId()).receiverId(savedChatMessage.getReceiverId()).message(savedChatMessage.getContent()).build());
    }

    @GetMapping("/messages/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable("senderId") String senderId,@PathVariable("receiverId") String receiverId) {
        return ResponseEntity.ok( chatMessageService.findChatMessages(senderId, receiverId));
    }

}
