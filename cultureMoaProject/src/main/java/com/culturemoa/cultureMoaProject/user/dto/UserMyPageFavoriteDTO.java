package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 사용자가 선택한 선호도의 배열값
 * favorites : 사요자가 선택한 선호도 idx 값
 * userIdx : 사용자 idx 값
 * sDate : insert 날짜
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMyPageFavoriteDTO {
    private List<?> favorites;
    private int userIdx;
    private LocalDateTime sDate;
}
