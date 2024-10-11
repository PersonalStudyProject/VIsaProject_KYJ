package com.yeonjin.chatproject.repository;

import com.yeonjin.chatproject.model.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {

    //대화방 id에 따라 메세지를 가져오는 메서드 추가
    List<ChatEntity> findByRoomId(String roomId);

}
