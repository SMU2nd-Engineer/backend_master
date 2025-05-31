package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 판매자 정보와 거래 idx 조회하기 위한 DTO
 * sellerName : 판매자 이름
 * transactionIdx : 유저 거래 테이블의 idx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerInfoDTO {
    private String sellerName;
    private int transactionIdx;
}
