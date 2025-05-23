package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 판매자 정보를 조회하기 위한 DTO
 * sellerName : 판매자 이름
 * sellerIdx : 판매자 고유 idx (리뷰 페이지 남기기 위해 사용)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerInfoDTO {
    private String sellerName;
    private int sellerIdx;
}
