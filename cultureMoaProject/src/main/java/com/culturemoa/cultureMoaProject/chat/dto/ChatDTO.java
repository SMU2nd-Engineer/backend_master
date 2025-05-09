package com.culturemoa.cultureMoaProject.chat.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document(collection = "chat")
@Getter
@Setter
public class ChatDTO {
    @Id
    private Long id;
    private Long user_idx;
    private Long chatRoom_id;
    private String content;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime sendTime;

    public ChatDTO(Long id, Long user_idx, Long chatRoom_id, String content, LocalDateTime sendTime) {
        this.id = id;
        this.user_idx = user_idx;
        this.chatRoom_id = chatRoom_id;
        this.content = content;
        this.sendTime = sendTime;
    }
}
