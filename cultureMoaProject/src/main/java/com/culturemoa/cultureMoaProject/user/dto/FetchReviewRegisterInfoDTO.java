package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 사용자가 작성한 리뷰 정보를 가져올 DTO
 * reviewIdx : 리뷰 IDX
 * sellerIdx : 판매자 IDX
 * sellerName : 판매자 이름
 * productTitle : 상품 이름
 * rating : 별점
 * reviewText : 거래 후기
 * sDate : 작성 일자
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetchReviewRegisterInfoDTO {
    private int reviewIdx;
    private int sellerIdx;
    private String sellerName;
    private String productTitle;
    private double rating;
    private String reviewText;
    private LocalDate sDate;
}
