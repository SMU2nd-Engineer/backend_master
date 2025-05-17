package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 마이페이지 메인에 전달할 데이터 모음
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageMainDTO {
    private MyPageAverageRatingDTO myPageAverageRatingDTO;
}
