package com.culturemoa.cultureMoaProject.chat.repository;

import com.culturemoa.cultureMoaProject.chat.dto.ChatRoomDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoomDTO, String> {
    @Query("{ 'flag': { '$ne': false } }")
    List<ChatRoomDTO> findByUsersContainingOrderByLastMessageAtDesc(Long userIdx);
    Optional<ChatRoomDTO> findById(Long chatRoomId);
}
