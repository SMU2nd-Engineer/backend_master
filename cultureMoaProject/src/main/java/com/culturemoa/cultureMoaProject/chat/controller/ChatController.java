package com.culturemoa.cultureMoaProject.chat.controller;

import com.culturemoa.cultureMoaProject.chat.dto.ChatDTO;
import com.culturemoa.cultureMoaProject.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{id}")
    public ChatDTO getChatById(@PathVariable String id) {
        return chatService.getChatByid(id);
    }

    @GetMapping
    public List<ChatDTO> getAllChat(){
        return chatService.getAllChat();
    }

    @PostMapping
    public ChatDTO createChat(@RequestBody ChatDTO chatDTO){
        return chatService.createChat(chatDTO);
    }
}
