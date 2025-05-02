package com.culturemoa.cultureMoaProject.user.dto;

import java.time.LocalDateTime;

public class UserDTO {
    private Long idx;
    private String id;
    private String password;
    private String name;
    private String email;
    private String address;
    private String nickName;
    private LocalDateTime sDate;
    private LocalDateTime eDate;

    public UserDTO(Long idx, String id, String password, String name, String email, String address, String nickName, LocalDateTime sDate, LocalDateTime eDate) {
        this.idx = idx;
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.nickName = nickName;
        this.sDate = sDate;
        this.eDate = eDate;
    }

    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public LocalDateTime getsDate() {
        return sDate;
    }

    public void setsDate(LocalDateTime sDate) {
        this.sDate = sDate;
    }

    public LocalDateTime geteDate() {
        return eDate;
    }

    public void seteDate(LocalDateTime eDate) {
        this.eDate = eDate;
    }
}
