package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 거래 후 리뷰 페이지에 추가로 필요한 데이터를 요청 받았을 때 전달 받은 데이터를 담을 dto
 * sellerIdx : 거래 후 넘겨 받은 정보에서 추출한 판매자 idx
 * productIdx : 거래 후 넘겨 받은 정보에서 추출한 상품 idx
 * userId : 구매자 아이디를 넘겨 받음.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInitInfoDTO {
    private String sellerIdx;
    private String productIdx;
    private String userId;
}
