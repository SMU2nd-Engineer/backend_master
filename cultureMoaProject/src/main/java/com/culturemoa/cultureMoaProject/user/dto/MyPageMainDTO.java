package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 마이페이지 메인에 전달할 데이터 모음
 * myPageAverageRatingDTO : 평균 별점을 담을 dto
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageMainDTO {
    private MyPageAverageRatingDTO myPageAverageRating;
    private List<MyPageProductListDTO> myMainSellProductList;
    private List<MyPageProductListDTO> myMainPeakList;
    private List<ReviewListDTO> myMainReview;
}
