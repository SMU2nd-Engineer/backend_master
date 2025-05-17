package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 토큰에서 추출한 id를 사용하여 평점을 조회 및 합산해서 가져올 dto
 * myPageTotalRating : 합산한 결과를 받아올 dto 0도 있을 수 있음.
 * totalRatingCount : 평균을 프론트에서 처리하기 위하여 지정한 값.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageAverageRatingDTO {
    public double myPageTotalRating;
}
