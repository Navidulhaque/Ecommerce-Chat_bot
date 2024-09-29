package com.navidul.ChattyPro.chatRoom.service;

import com.navidul.ChattyPro.chatRoom.entity.ChatRoom;
import com.navidul.ChattyPro.chatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository repository;

    public Optional<String> getChatRoomId(String senderId,String receiverId,boolean createNewRoom) {
        return repository.findBySenderIdAndReceiverId(senderId,receiverId).map(ChatRoom::getChatId).or(()->{
            if(createNewRoom) {
                String chatId = createChatId(senderId,receiverId);
                return Optional.of(chatId);
            }
            else{
                return Optional.empty();
            }
        });
    }

    private String createChatId(String senderId, String receiverId) {

        String chatId = String.format("%s_%s", senderId, receiverId);
        ChatRoom senderReceiver = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        ChatRoom receiverSender = ChatRoom.builder()
                .chatId(chatId)
                .senderId(receiverId)
                .receiverId(senderId)
                .build();
        repository.save(senderReceiver);
        repository.save(receiverSender);
        return chatId;
    }
}
