package com.yeonjin.chatproject.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name="chat_entity")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;  // 채팅 메시지 내용

    @Column(nullable = false)
    private String sender;   // 보낸 사람

    @Column(nullable = false)
    private LocalDateTime timestamp;  // 메시지 보낸 시간

    @Column
    private String roomId;  // 대화방 ID (optional)

    @Column
    private String receiver;  // 수신자정보 (optional)
}
