package com.culturemoa.cultureMoaProject.chat.service;

import com.culturemoa.cultureMoaProject.chat.dto.ChatDTO;
import com.culturemoa.cultureMoaProject.chat.dto.ChatRoomDTO;
import com.culturemoa.cultureMoaProject.chat.repository.ChatRepository;
import com.culturemoa.cultureMoaProject.chat.repository.ChatRoomRepository;
import com.culturemoa.cultureMoaProject.common.counters.service.CountersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.relational.core.sql.Where;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class ChatService {
    private final ChatRepository chatRepository;
    private final MongoOperations mongoOperations;

    @Autowired
    public ChatService(ChatRepository chatRepository, MongoOperations mongoOperations){
        this.chatRepository = chatRepository;
        this.mongoOperations = mongoOperations;
    }

    public ChatDTO getChatByid(String id){
        return chatRepository.findById(id).orElse(null);
    }

    /**
     * 채팅방의 채팅 내용 조회
     * @param chatRoomId 채팅방 id
     * @return 채팅 목록
     */
    public List<ChatDTO> getChatsByRoomId(Long chatRoomId){
        return mongoOperations.find(Query.query(Criteria.where("chatRoom_id").is(chatRoomId)),ChatDTO.class);
    }

    /**
     * 채팅 생성 메서드
     * @param chatDTO 채팅 정보
     * @return 생성된 채팅 내용
     */
    public ChatDTO createChat(ChatDTO chatDTO){
        chatDTO.setSendTime(LocalDateTime.now());
        return chatRepository.save(chatDTO);
    }

    /**
     * 채팅 삭제 메서드
     * @param id 채팅 id
     */
    public void deleteChat(Long id) {
        mongoOperations.findAndRemove(Query.query(Criteria.where("id").is(id)), ChatDTO.class);
    }
}
