package com.yeonjin.chatproject.service;

import com.yeonjin.chatproject.model.entity.ChatEntity;
import com.yeonjin.chatproject.repository.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    // 대화방 ID로 메시지 가져오기
    public List<ChatEntity> getMessagesByRoomId(String roomId) {
        return chatRepository.findByRoomId(roomId);
    }

    //메시지 저장
    public ChatEntity saveMessage(String message, String sender, String roomId, String receiver) {
        // ChatEntity 객체 생성 및 저장
        ChatEntity chatEntity = ChatEntity.builder()
                .message(message)
                .sender(sender)
                .roomId(roomId)
                .receiver(receiver)
                .timestamp(LocalDateTime.now())  // 현재 시간 저장
                .build();

        return chatRepository.save(chatEntity);
    }


}
