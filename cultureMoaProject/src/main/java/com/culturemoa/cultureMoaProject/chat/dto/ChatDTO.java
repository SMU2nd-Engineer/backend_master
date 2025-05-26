package com.culturemoa.cultureMoaProject.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "chat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    @Id
    private Long id;
    private Long userIdx;
    private Long chatRoomId;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Instant createdAt = Instant.now();
}
