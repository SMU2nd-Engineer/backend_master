package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원 가입 후 응답 dto 회원 가입 후 응답을 반환하기 위하여 사용(일관성 및 확장성을 위해서 만들어둠)
 *  id
 *  nickname
 *  welcomeMessage
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterResponseDTO {
    private String id;
    private String nickname;
    private String welcomeMessage;
}
