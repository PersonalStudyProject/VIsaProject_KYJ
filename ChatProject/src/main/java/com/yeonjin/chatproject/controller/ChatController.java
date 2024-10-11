package com.yeonjin.chatproject.controller;

import com.yeonjin.chatproject.model.entity.ChatEntity;
import com.yeonjin.chatproject.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@Tag(name="Spring boot Swagger 연동 API(USER 기능)")
@Slf4j
@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    @Autowired //생성자 주입을 사용할경우 생략이 가능함
    private ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/save")
    public ResponseEntity<ChatEntity> saveChatEntity(@RequestBody ChatEntity chatEntity){
        ChatEntity savedMessage = chatService.saveMessage(
                chatEntity.getMessage(),
                chatEntity.getSender(),
                chatEntity.getRoomId(),
                chatEntity.getReceiver()
        );
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

}
