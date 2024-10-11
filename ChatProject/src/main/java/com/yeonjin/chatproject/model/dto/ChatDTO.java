package com.yeonjin.chatproject.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatDTO {

    private Long id;
    private String message;  //내용
    private String sender;  //보낸사람
    private LocalDateTime timestamp;   //보낸시간
    private String roomId;   //대화방 ID
    private String receiver;  //수신자
}
