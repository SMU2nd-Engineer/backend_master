package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 리뷰 남기기 페이지에서 초기에 사용할 정보를 담을 DTO
 * sellerInfo : 거래 정보를 가져옴.
 * evaluationCategories : 거래 평가 항목이 담김
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewWritingPageInfoDTO {
    private SellerInfoDTO sellerInfo;
    private List<UserCategorySubDTO> evaluationCategories;
}
