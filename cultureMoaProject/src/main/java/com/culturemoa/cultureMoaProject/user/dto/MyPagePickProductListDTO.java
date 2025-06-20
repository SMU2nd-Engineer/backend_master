package com.culturemoa.cultureMoaProject.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 찜 목록, 판매, 구매 등 상품 정보를 담을 DTO
 * title : 상품 제목
 * imageUrl : 이미지 경로
 * price : 가격
 * text : 상품 내용
 * flag : 판매 여부(ERD에는 boolean이지만 db에는 1또는 0일 것이므로 int로 받음)
 * pick : 찜 여부
 * categoryIdx : 상품의 분류
 * categoryGenreIdx : 상품의 장류 분류
 * eDate : 상품의 판매 또는 삭제 일자.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPagePickProductListDTO {
    private int idx;
    private String title;
    @JsonProperty("image_Url")
    private String imageUrl;
    private String price;
    private String content;
    private int flag;
    private int categoryIdx;
    private int categoryGenreIdx;
    private LocalDateTime eDate;
    private int pick;
}
