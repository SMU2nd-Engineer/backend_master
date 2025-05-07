package com.culturemoa.cultureMoaProject.chat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document(collection = "chat")
public class ChatDTO {
    @Id
    private String id;
    private String idx;
    private Long user_idx;
    private Long chatRoom_id;
    private String content;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime sendTime;

    public ChatDTO(String id, String idx, Long user_idx, Long chatRoom_id, String content, LocalDateTime sendTime) {
        this.id = id;
        this.idx = idx;
        this.user_idx = user_idx;
        this.chatRoom_id = chatRoom_id;
        this.content = content;
        this.sendTime = sendTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public Long getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(Long user_idx) {
        this.user_idx = user_idx;
    }

    public Long getChatRoom_id() {
        return chatRoom_id;
    }

    public void setChatRoom_id(Long chatRoom_id) {
        this.chatRoom_id = chatRoom_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
