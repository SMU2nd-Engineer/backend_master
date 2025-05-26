package com.culturemoa.cultureMoaProject.chat.repository;

import com.culturemoa.cultureMoaProject.chat.dto.RoomReadDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RoomReadRepository extends MongoRepository<RoomReadDTO, String> {
    Optional<RoomReadDTO> findByUserIdxAndChatRoomId(Long userIdx, Long chatRoomId);
    List<RoomReadDTO> findByUserIdx(Long userIdx);
}
