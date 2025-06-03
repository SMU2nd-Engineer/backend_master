package com.culturemoa.cultureMoaProject.chat.service;

import com.culturemoa.cultureMoaProject.chat.ChatWebSocketHandler;
import com.culturemoa.cultureMoaProject.chat.dto.*;
import com.culturemoa.cultureMoaProject.chat.repository.ChatRepository;
import com.culturemoa.cultureMoaProject.chat.repository.ChatRoomRepository;
import com.culturemoa.cultureMoaProject.chat.repository.RoomReadRepository;
import com.culturemoa.cultureMoaProject.common.util.HandleAuthentication;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RoomReadRepository roomReadRepository;
    private final MongoOperations mongoOperations;
    private final HandleAuthentication handleAuthentication;
    private final UserDAO userDAO;
    private final ObjectMapper objectMapper;
    private final ChatWebSocketHandler webSocketHandler;

    @Autowired
    public ChatService(ChatRepository chatRepository, ChatRoomRepository chatRoomRepository, RoomReadRepository roomReadRepository, MongoOperations mongoOperations, HandleAuthentication handleAuthentication, UserDAO userDAO, ObjectMapper objectMapper, ChatWebSocketHandler webSocketHandler) {
        this.chatRepository = chatRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.roomReadRepository = roomReadRepository;
        this.mongoOperations = mongoOperations;
        this.handleAuthentication = handleAuthentication;
        this.userDAO = userDAO;
        this.objectMapper = objectMapper;
        this.webSocketHandler = webSocketHandler;
    }

    public ChatDTO getChatByid(String id){
        return chatRepository.findById(id).orElse(null);
    }

    /**
     * 채팅방의 채팅 내용 조회
     * @param chatRoomId 채팅방 id
     * @return 채팅 목록
     */
    public ChatListDTO getChatsByChatRoomId(Long chatRoomId){
        int userIdx = userDAO.getUserIdx(handleAuthentication.getUserIdByAuth());

        // 읽음 시간 갱신 (보낸 사람만)
        RoomReadDTO status = new RoomReadDTO(Long.valueOf(userIdx), chatRoomId);
        status.setLastReadAt(Instant.now());
        roomReadRepository.save(status);

        List<ChatDTO> chatList = chatRepository.findByChatRoomId(chatRoomId);

        return new ChatListDTO(Long.valueOf(userIdx), chatList);
    }

    /**
     * 채팅 생성 메서드
     * @param chatDTO 채팅 정보
     * @return 생성된 채팅 내용
     */
    public ChatDTO saveChat(ChatDTO chatDTO){
        Long userIdx = Long.valueOf(userDAO.getUserIdx(handleAuthentication.getUserIdByAuth()));
        chatDTO.setUserIdx(userIdx);

        // 채팅방 업데이트
        ChatRoomDTO room = chatRoomRepository.findById(chatDTO.getChatRoomId()).orElseThrow();
        room.setLastMessage(chatDTO.getContent());
        room.setLastMessageAt(Instant.now());
        chatRoomRepository.save(room);

        // 읽음 시간 갱신 (보낸 사람만)
        RoomReadDTO status = new RoomReadDTO(userIdx, chatDTO.getChatRoomId());
        status.setLastReadAt(Instant.now());
        roomReadRepository.save(status);

        try {
            String json = objectMapper.writeValueAsString(chatDTO);
            webSocketHandler.broadcastToRoom(String.valueOf(chatDTO.getChatRoomId()), json, userIdx);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return chatRepository.save(chatDTO);
    }

    /**
     * 채팅 삭제 메서드
     * @param id 채팅 id
     */
    public void deleteChat(Long id) {
        mongoOperations.findAndRemove(Query.query(Criteria.where("id").is(id)), ChatDTO.class);
    }

    /**
     * 채팅방 생성 메서드
     * flag 값 true로 고정 생성 - true = 사용중인 채팅방
     * @param chatRoomDTO 채팅방 DTO
     * @return 생성된 채팅방 정보
     */
    public ChatRoomDTO createChatRoom(ChatRoomDTO chatRoomDTO){
        Long userIdx = Long.valueOf(userDAO.getUserIdx(handleAuthentication.getUserIdByAuth()));
        chatRoomDTO.getUsers().add(userIdx);

        List<ChatRoomDTO> tmp = chatRoomRepository.findByUsersAll(chatRoomDTO.getUsers());

        if(!tmp.isEmpty()) return tmp.get(0);

        chatRoomDTO.setFlag(true);
        chatRoomDTO.setLastMessageAt(Instant.now());

        return chatRoomRepository.save(chatRoomDTO);
    }

    /**
     * 채팅방 내역 GET 메서드
     * @return 채팅방 리스트
     */
    public List<ChatRoomInfoDTO> getChatRooms(){
        Long userIdx = Long.valueOf(userDAO.getUserIdx(handleAuthentication.getUserIdByAuth()));

        List<ChatRoomDTO> rooms = chatRoomRepository.findByUsersContaining(userIdx, Sort.by(Sort.Direction.DESC, "lastMessageAt"));
        List<ChatRoomInfoDTO> roomInfos = new ArrayList<>();

        rooms.forEach((room) -> {
            ChatRoomInfoDTO info = new ChatRoomInfoDTO();
            info.setId(room.getId());
            info.setTitle(room.getTitle());
            info.setUsers(room.getUsers());
            info.setLastMessage(room.getLastMessage());
            info.setLastMessageAt(room.getLastMessageAt());
            info.setCount(countUnreadMessages(userIdx, room.getId()));

            info.getUsers().forEach((user)-> {
                if (user.longValue() != userIdx.longValue())
                    info.setNickname(userDAO.getNicknameByUserIdx(user));
            });
            roomInfos.add(info);
        });
        return roomInfos;
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


    public long countUnreadMessages(Long userIdx, Long chatRoomId) {
        RoomReadDTO roomReadDTO = roomReadRepository.findByUserIdxAndChatRoomId(userIdx, chatRoomId)
                .orElse(new RoomReadDTO(userIdx, chatRoomId));
        Instant lastReadAt = roomReadDTO.getLastReadAt();

        return chatRepository.countByChatRoomIdAndCreatedAtAfter(chatRoomId, lastReadAt);
    }
}
