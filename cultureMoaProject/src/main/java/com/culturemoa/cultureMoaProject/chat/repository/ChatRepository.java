package com.culturemoa.cultureMoaProject.chat.repository;

import com.culturemoa.cultureMoaProject.chat.dto.ChatDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<ChatDTO, String> {
}
