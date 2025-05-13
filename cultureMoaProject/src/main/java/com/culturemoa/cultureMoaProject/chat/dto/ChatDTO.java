package com.culturemoa.cultureMoaProject.chat.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document(collection = "chat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    @Id
    private Long id;
    private Long user_idx;
    private Long chatRoom_id;
    private String content;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime sendTime;
}
