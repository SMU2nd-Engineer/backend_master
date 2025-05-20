package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * 관심사 수정을 위한 정보를 담을 DTO
 * insertNewFavorites : 기존에 관심사가 아닌 추가된 관심사 번호가 담긴 배열
 * notFavorites : 관신사였다가 관심사가 아니게 된 관심사 번호가 담긴 배열
 * userIdx : 유저 IDX
 * sDate : 생성일자 
 * eDate : 수정일자
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageEditFavoriteDTO {
    private List<Integer> insertNewFavorites;
    private List<Integer> notFavorites;
    private int userIdx;
    private LocalDateTime sDate;
    private LocalDateTime eDate;
}
