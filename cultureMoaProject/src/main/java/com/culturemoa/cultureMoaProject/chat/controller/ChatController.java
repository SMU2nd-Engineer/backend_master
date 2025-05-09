package com.culturemoa.cultureMoaProject.chat.controller;

import com.culturemoa.cultureMoaProject.chat.dto.ChatDTO;
import com.culturemoa.cultureMoaProject.chat.service.ChatService;
import com.culturemoa.cultureMoaProject.common.counters.service.CountersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final CountersService countersService;
    private static final String type = "chat";

    @Autowired
    public ChatController(ChatService chatService, CountersService countersService) {
        this.chatService = chatService;
        this.countersService = countersService;
    }

    @GetMapping("/{id}")
    public ChatDTO getChatById(@PathVariable String id) {
        return chatService.getChatByid(id);
    }

    @GetMapping("/room/{chatRoom_id}")
    public List<ChatDTO> getChatsByRoomId(@PathVariable Long chatRoom_id){
        return chatService.getChatsByRoomId(chatRoom_id);
    }

    @PostMapping
    public ChatDTO createChat(@RequestBody ChatDTO chatDTO){
        chatDTO.setId(countersService.getId(type));
        return chatService.createChat(chatDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable Long id){
        chatService.deleteChat(id);
    }
}
