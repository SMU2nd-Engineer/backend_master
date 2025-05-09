package com.culturemoa.cultureMoaProject.chat.controller;

import com.culturemoa.cultureMoaProject.chat.dto.ChatRoomDTO;
import com.culturemoa.cultureMoaProject.chat.service.ChatRoomService;
import com.culturemoa.cultureMoaProject.common.counters.service.CountersService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatroom")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final CountersService countersService;
    private static final String type = "chatroom";

    public ChatRoomController(ChatRoomService chatRoomService, CountersService countersService) {
        this.chatRoomService = chatRoomService;
        this.countersService = countersService;
    }

    @PostMapping
    public ChatRoomDTO createChatRoom(@RequestBody ChatRoomDTO chatRoomDTO){
        chatRoomDTO.setId(countersService.getId(type));

        return chatRoomService.createChatRoom(chatRoomDTO);
    }

    @GetMapping("/{userIdx}")
    public List<ChatRoomDTO> getChatRoomsByUserIdx(@PathVariable Long userIdx){
        return chatRoomService.getChatRooms(userIdx);
    }

    @PatchMapping
    public ChatRoomDTO updateChatRoomTitle(@RequestBody ChatRoomDTO chatRoomDTO){
        return chatRoomService.updateChatRoomTitle(chatRoomDTO);
    }

    @DeleteMapping("/{chatRoomId}")
    public Long deleteChatRoom(@PathVariable Long chatRoomId){
        return chatRoomService.updateChatRoomFlag(chatRoomId);
    }
}
