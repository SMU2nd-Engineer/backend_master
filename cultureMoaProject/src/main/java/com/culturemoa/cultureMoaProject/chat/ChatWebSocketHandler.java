package com.culturemoa.cultureMoaProject.chat;

import com.culturemoa.cultureMoaProject.chat.dto.ChatDTO;
import com.culturemoa.cultureMoaProject.chat.dto.RoomReadDTO;
import com.culturemoa.cultureMoaProject.chat.repository.ChatRepository;
import com.culturemoa.cultureMoaProject.chat.repository.RoomReadRepository;
import com.culturemoa.cultureMoaProject.common.jwt.JwtProvider;
import com.culturemoa.cultureMoaProject.common.util.HandleAuthentication;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatWebSocketHandler extends TextWebSocketHandler {
    // key: roomId, value: 접속한 유저 세션 리스트
    private final Map<String, Set<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    // 유저 세션에 userId나 기타 정보도 같이 저장하고 싶다면
    private final Map<WebSocketSession, ChatSessionInfo> sessionInfoMap = new ConcurrentHashMap<>();

    private final ChatRepository chatRepository;
    private final RoomReadRepository roomReadRepository;
    private final JwtProvider jwtProvider;
    private final UserDAO userDAO;

    @Autowired
    public ChatWebSocketHandler(ChatRepository chatRepository, RoomReadRepository roomReadRepository, JwtProvider jwtProvider, UserDAO userDAO) {
        this.chatRepository = chatRepository;
        this.roomReadRepository = roomReadRepository;
        this.jwtProvider = jwtProvider;
        this.userDAO = userDAO;
    }

    public Map<String, Set<WebSocketSession>> getRoomSessions() {
        return roomSessions;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception  {
        // 연결 후 대기
        System.out.println("WebSocket connected: " + session.getId());
        // 예시: Query 파라미터에서 정보 추출 (ex: ws://.../chat?roomId=123&userId=kim)
        URI uri = session.getUri();
        String query = uri.getQuery(); // roomId=123&userId=kim
        Map<String, String> params = parseQueryParams(query);

        String roomId = params.get("roomId");

        String userId = String.valueOf(userDAO.getUserIdx(jwtProvider.getUserInfoByToken(params.get("token"))));

        if (roomId != null && userId != null) {
            roomSessions.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(session);
            sessionInfoMap.put(session, new ChatSessionInfo(userId, roomId));
            System.out.println("✅ WebSocket 연결: " + userId + " in room " + roomId);
        } else {
            session.close(CloseStatus.BAD_DATA);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            ChatSessionInfo senderInfo = sessionInfoMap.get(session);
            if (senderInfo == null) {
                session.close(CloseStatus.BAD_DATA);
                return;
            }

            String roomId = senderInfo.getRoomId();

            Optional<RoomReadDTO> lastAt = roomReadRepository.findByUserIdxAndChatRoomId(Long.valueOf(senderInfo.getUserId()),Long.valueOf(senderInfo.getRoomId()));

            List<ChatDTO> recentMessages = chatRepository.findByChatRoomIdAndCreatedAtAfter(Long.parseLong(roomId), lastAt.get().getLastReadAt());

            for (ChatDTO msg : recentMessages) {
                String json = objectMapper.writeValueAsString(msg);
                session.sendMessage(new TextMessage(json));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void broadcastToRoom(String roomId, String message, Long senderId) {
        Set<WebSocketSession> sessions = roomSessions.get(roomId);
        if (sessions != null) {
            for (WebSocketSession session : sessions) {
                ChatSessionInfo info = sessionInfoMap.get(session); // ← 여기 수정
                if(!Long.valueOf(info.getUserId()).equals(senderId)){
                    try {
                        session.sendMessage(new TextMessage(message));
                    } catch (Exception e) {
                        System.err.println("메시지 전송 실패: " + e.getMessage());
                    }
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        ChatSessionInfo info = sessionInfoMap.remove(session);
        if (info != null) {
            Set<WebSocketSession> sessions = roomSessions.get(info.getRoomId());
            if (sessions != null) {
                sessions.remove(session);
                if (sessions.isEmpty()) {
                    roomSessions.remove(info.getRoomId());
                }
            }
        }
        System.out.println("❌ WebSocket 연결 종료됨: " + session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public Map<String, String> parseQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2);
                if (keyValue.length == 2) {
                    params.put(URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8),
                            URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
                }
            }
        }
        return params;
    }

    private class ChatSessionInfo {
        private final String userId;
        private final String roomId;

        public ChatSessionInfo(String userId, String roomId) {
            this.userId = userId;
            this.roomId = roomId;
        }

        public String getUserId() { return userId; }
        public String getRoomId() { return roomId; }
    }

}
