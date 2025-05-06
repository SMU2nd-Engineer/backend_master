package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * UserDTO
 * 유저 정보를 전달할 객체
 * userIdx : 회원 고유 번호
 * userId : 회원 아이디
 * name : 회원 이름
 * password : 회원 비밀번호
 * socialLogin : 소셜 로그인 여부
 * withdrawnMember : 회원 탈퇴 여부
 * regDate : 회원 등록 시간
 */

// 테스트할 때 LoginRequestDTO 파일을 대체
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userIdx;
    private String userId;
    private String name;
    private String password;
    private String socialLogin;
    private String withdrawnMember;
    private LocalDateTime regDate;

    // mybatis 사용에 따라 없어질 수 있으며 DAO를 위해서 추가 가능성 있음.
    public UserDTO(String userid, String password, String name, LocalDateTime regdate) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regDate = regDate;
    }
}
