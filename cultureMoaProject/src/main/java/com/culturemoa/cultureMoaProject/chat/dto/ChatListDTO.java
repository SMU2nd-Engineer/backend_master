package com.culturemoa.cultureMoaProject.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
public class ChatListDTO {
    private Long fromidx;
    private List<ChatDTO> chatList;
}
