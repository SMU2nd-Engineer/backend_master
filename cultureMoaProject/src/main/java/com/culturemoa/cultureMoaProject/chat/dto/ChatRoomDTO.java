package com.culturemoa.cultureMoaProject.chat.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document(collection = "chatRoom")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {
    @Id
    private Long id;
    private String title;
    private Long fromUser;
    private Long toUser;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime time;
    private boolean flag;
}
