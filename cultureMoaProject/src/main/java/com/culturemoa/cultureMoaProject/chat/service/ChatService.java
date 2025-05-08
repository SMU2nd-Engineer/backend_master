package com.culturemoa.cultureMoaProject.chat.service;

import com.culturemoa.cultureMoaProject.chat.dto.ChatDTO;
import com.culturemoa.cultureMoaProject.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChatService {
    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository){
        this.chatRepository = chatRepository;
    }

    public ChatDTO getChatByid(String id){
        return chatRepository.findById(id).orElse(null);
    }

    public List<ChatDTO> getAllChat(){
        return chatRepository.findAll();
    }

    public ChatDTO createChat(ChatDTO chatDTO){
        return chatRepository.save(chatDTO);
    }
}
