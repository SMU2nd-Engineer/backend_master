package com.culturemoa.cultureMoaProject.chat.repository;

import com.culturemoa.cultureMoaProject.chat.dto.ChatRoomDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoomDTO, String> {
    @Query("{ 'flag': { '$ne': false }, 'users': ?0 }")
    List<ChatRoomDTO> findByUsersContaining(Long userIdx, Sort sort);
    @Query("{ 'users' : {$all: ?0 },'flag': { '$ne': false } }")
    List<ChatRoomDTO> findByUsersAll(List<Long> users);
    Optional<ChatRoomDTO> findById(Long chatRoomId);
}
