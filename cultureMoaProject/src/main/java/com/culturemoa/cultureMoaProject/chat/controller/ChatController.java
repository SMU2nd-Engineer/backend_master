package com.culturemoa.cultureMoaProject.chat.controller;

import com.culturemoa.cultureMoaProject.chat.dto.ChatDTO;
import com.culturemoa.cultureMoaProject.chat.dto.ChatRoomDTO;
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

    @Autowired
    public ChatController(ChatService chatService, CountersService countersService) {
        this.chatService = chatService;
        this.countersService = countersService;
    }

    @GetMapping("/{id}")
    public ChatDTO getChatById(@PathVariable String id) {
        return chatService.getChatByid(id);
    }

    @GetMapping("/messages")
    public List<ChatDTO> getChatsByChatRoomId(@RequestParam("chatRoomId") Long chatRoomId){
        return chatService.getChatsByChatRoomId(chatRoomId);
    }

    @PostMapping
    public ChatDTO saveChat(@RequestBody ChatDTO chatDTO){
        chatDTO.setId(countersService.getId("chat"));

        return chatService.saveChat(chatDTO);
    }

    @PostMapping("/rooms")
    public ChatRoomDTO CreateChatRoom(@RequestBody ChatRoomDTO chatRoomDTO){
        chatRoomDTO.setId(countersService.getId("chatroom"));

        return chatService.createChatRoom(chatRoomDTO);
    }

    @GetMapping("/rooms")
    public List<ChatRoomDTO> getChatRoomsByUserIdx(){
        return chatService.getChatRooms();
    }

    @DeleteMapping("/rooms/{chatRoomId}")
    public Long deleteChatRoom(@PathVariable Long chatRoomId){
        return chatService.updateChatRoomFlag(chatRoomId);
    }
}
