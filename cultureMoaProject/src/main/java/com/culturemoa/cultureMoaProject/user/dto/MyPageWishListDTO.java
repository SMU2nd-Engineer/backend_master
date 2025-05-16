package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 찜 목록을 조회한 데이터를 담아서 프론트에 던져줄 DTO
 * title : 상품 제목
 * imageUrl : 이미지 경로
 * price : 가격
 * text : 상품 내용
 * flag : 판매 여부(ERD에는 boolean이지만 db에는 1또는 0일 것이므로 int로 받음)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageWishListDTO {
    private String title;
    private String imageUrl;
    private String price;
    private String text;
    private int flag;
}
