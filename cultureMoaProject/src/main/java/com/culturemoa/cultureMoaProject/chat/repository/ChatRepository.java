package com.culturemoa.cultureMoaProject.chat.repository;

import com.culturemoa.cultureMoaProject.chat.dto.ChatDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface ChatRepository extends MongoRepository<ChatDTO, String> {
    List<ChatDTO> findByChatRoomIdAndCreatedAtAfter(Long chatRoomId, Instant time);
    long countByChatRoomIdAndCreatedAtAfter(Long chatRoomId, Instant time);
    List<ChatDTO> findByChatRoomId(Long chatRoomId);
}
