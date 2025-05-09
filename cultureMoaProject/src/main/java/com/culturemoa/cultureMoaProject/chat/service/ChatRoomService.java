package com.culturemoa.cultureMoaProject.chat.service;

import com.culturemoa.cultureMoaProject.chat.dto.ChatRoomDTO;
import com.culturemoa.cultureMoaProject.chat.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MongoOperations mongoOperations;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository, MongoOperations mongoOperations) {
        this.chatRoomRepository = chatRoomRepository;
        this.mongoOperations = mongoOperations;
    }

    /**
     * 채팅방 생성 메서드
     * flag 값 true로 고정 생성 - true = 사용중인 채팅방
     * @param chatRoomDTO 채팅방 DTO
     * @return 생성된 채팅방 정보
     */
    public ChatRoomDTO createChatRoom(ChatRoomDTO chatRoomDTO){
        chatRoomDTO.setFlag(true);
        chatRoomDTO.setTime(LocalDateTime.now());
        return chatRoomRepository.save(chatRoomDTO);
    }

    /**
     * 채팅방 내역 GET 메서드
     * @param userIdx 유저 IDX 값
     * @return 채팅방 리스트
     */
    public List<ChatRoomDTO> getChatRooms(Long userIdx){
        return mongoOperations.find(
                Query.query(
                    new Criteria().orOperator(
                        Criteria.where("fromUser").is(userIdx)
                        ,Criteria.where("toUser").is(userIdx)
                    ).andOperator(Criteria.where("flag").is(true))
                )
                , ChatRoomDTO.class);
    }

    /**
     * 채팅방 제목 수정 메서드
     * @param chatRoomDTO 채팅방 정보
     * @return 변경된 채팅방 정보
     */
    public ChatRoomDTO updateChatRoomTitle (ChatRoomDTO chatRoomDTO){
        return mongoOperations.findAndModify(
                Query.query(Criteria.where("id").is(chatRoomDTO.getId())),
                new Update().pull("title", chatRoomDTO.getTitle()),
                ChatRoomDTO.class );
    }

    /**
     * 채팅방 flag 변경 메서드(사용자 삭제)
     * @param chatRoomId 채팅방 id 값
     * @return 영향 받은 데이터 수 (삭제 완료시 1)
     */
    public Long updateChatRoomFlag (Long chatRoomId){
        return mongoOperations.updateFirst(
                Query.query(Criteria.where("id").is(chatRoomId)),
                new Update().set("flag", false),
                ChatRoomDTO.class).getMatchedCount();
    }
}
