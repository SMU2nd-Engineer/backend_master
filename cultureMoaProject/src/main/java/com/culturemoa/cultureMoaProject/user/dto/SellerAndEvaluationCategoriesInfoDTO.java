package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 리뷰 남기기 페이지에서 초기에 사용할 정보를 담을 DTO
 * Seller : 판매자 이름
 * evaluationCategories : 거래 평가 항목이 담김
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerAndEvaluationCategoriesInfoDTO {
    private SellerInfoDTO sellerInfo;
    private List<UserCategorySubDTO> evaluationCategories;
}
