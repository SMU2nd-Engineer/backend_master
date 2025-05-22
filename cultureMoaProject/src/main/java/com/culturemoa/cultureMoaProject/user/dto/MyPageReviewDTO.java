package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 마이페이지 리뷰 탭 에 넘길 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageReviewDTO {
    private List<ReviewListDTO> reviewLists;
    private MyPageAverageRatingDTO myAverageRating;
    private Map<String, Integer> myEvaluationList;
}
