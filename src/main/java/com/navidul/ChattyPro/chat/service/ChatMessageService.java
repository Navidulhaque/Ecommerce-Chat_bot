package com.navidul.ChattyPro.chat.service;

import com.navidul.ChattyPro.chat.entity.ChatMessage;
import com.navidul.ChattyPro.chat.repository.ChatMessageRepository;
import com.navidul.ChattyPro.chatRoom.service.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        String chatId = chatRoomService.getChatRoomId(chatMessage.getSenderId(),chatMessage.getReceiverId(),true).orElseThrow();
        chatMessage.setChatId(chatId);
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> findChatMessages(String senderId, String receiverId) {

        System.out.println(senderId);
        System.out.println(receiverId);
        String chatId = chatRoomService.getChatRoomId(senderId,receiverId,false).orElseThrow();
        return findMessagesByChatId(chatId);
    }

    private List<ChatMessage> findMessagesByChatId(String chatId) {
        return chatMessageRepository.findByChatId(chatId);
    }

}
