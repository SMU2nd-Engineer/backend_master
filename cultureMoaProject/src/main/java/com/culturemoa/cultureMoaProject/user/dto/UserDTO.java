package com.culturemoa.cultureMoaProject.user.dto;

import java.time.LocalDateTime;

public class UserDTO {
    private Long userIdx;
    private String userId;
    private String name;
    private String password;
    private LocalDateTime regDate;

    public UserDTO(String userId, String password, String name, LocalDateTime regDate) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regDate = regDate;
    }

    public Long getUserIdx() {
        return userIdx;
    }
    public void setUserIdx(Long userIdx) {
        this.userIdx = userIdx;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDateTime getRegDate() {
        return regDate;
    }
    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }
}
