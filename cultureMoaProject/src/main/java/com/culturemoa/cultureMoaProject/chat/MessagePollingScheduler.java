package com.culturemoa.cultureMoaProject.chat;

import com.culturemoa.cultureMoaProject.chat.dto.ChatDTO;
import com.culturemoa.cultureMoaProject.chat.repository.ChatRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class MessagePollingScheduler {
    private final ChatRepository repository;
    private final ChatWebSocketHandler handler;
    private final ObjectMapper objectMapper;
    private final Map<String, Instant> lastCheckPerRoom = new ConcurrentHashMap<>();

    @Autowired
    public MessagePollingScheduler(ChatRepository repository, ChatWebSocketHandler handler, ObjectMapper objectMapper) {
        this.repository = repository;
        this.handler = handler;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelay = 3000)
    public void pollNewMessages() {
        handler.getRoomSessions().forEach((roomId, sessions) -> {
            Instant lastCheck = lastCheckPerRoom.getOrDefault(roomId, Instant.now().minusSeconds(3));

            List<ChatDTO> newMessages = repository.findByChatRoomIdAndCreatedAtAfter(Long.parseLong(roomId), lastCheck);
            if (!newMessages.isEmpty()) {
                newMessages.forEach(msg -> {
                    try {
                        String json = objectMapper.writeValueAsString(msg);
                        handler.broadcastToRoom(roomId, json, msg.getUserIdx());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                lastCheckPerRoom.put(roomId, Instant.now());
            }
        });
    }
}
