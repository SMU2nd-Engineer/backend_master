package com.culturemoa.cultureMoaProject.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "chatRoom")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {
    @Id
    private Long id;
    private String title;
    private List<Long> users;
    private String lastMessage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Instant lastMessageAt;
    private boolean flag;
}
