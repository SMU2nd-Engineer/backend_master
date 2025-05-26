package com.culturemoa.cultureMoaProject.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "roomread")
@Data
public class RoomReadDTO {
    @Id
    private String id; // userId_roomId

    private Long userIdx;
    private Long chatRoomId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Instant lastReadAt;

    public RoomReadDTO(Long userIdx, Long chatRoomId) {
        this.userIdx = userIdx;
        this.chatRoomId = chatRoomId;
        this.id = userIdx + "_" + chatRoomId; // 👈 여기서 ID를 자동 구성
        this.lastReadAt = Instant.EPOCH; // 기본값
    }

    // 기본 생성자 (필수)
    public RoomReadDTO() {}
}
