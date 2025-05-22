package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 사용자가 선택한 선호도의 컬럼 이름이 들어있는 dto
 * favorites : 사용자가 선택한 선호도를 컬럼명에 맞춰서 변환한 값
 * userIdx : 사용자 idx 값
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterFavoriteDTO {
    private List<String> favorites;
    private int userIdx;
}
