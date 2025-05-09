package com.culturemoa.cultureMoaProject.chat.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document(collection = "chatRoom")
@Getter
@Setter
public class ChatRoomDTO {
    @Id
    private Long id;
    private String title;
    private Long fromUser;
    private Long toUser;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime time;
    private boolean flag;

    public ChatRoomDTO(Long id, String title, Long fromUser, Long toUser, LocalDateTime time, boolean flag) {
        this.id = id;
        this.title = title;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.time = time;
        this.flag = flag;
    }
}
