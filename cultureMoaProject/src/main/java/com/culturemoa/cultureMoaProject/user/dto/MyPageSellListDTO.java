package com.culturemoa.cultureMoaProject.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 상품 판매 내역을 조회 정보를 담을 DTO
 *     idx : 상품 IDX
 *     title : 제목
 *     price : 가격
 *     flag : 판매 여부
 *     content : 상품 상세 내용
 *     imageUrl : 이미지 경로
 *     categorySubIdx : 카테고리 정보
 *     sDate : 상품 등록 정보
 *     eDate : 상품 삭제 또는 판매 일자
 *     pick : 찜 여부 파악하기
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageSellListDTO {
    private int idx;
    private String title;
    private int price;
    private int flag;
    private String content;
    @JsonProperty("image_Url") // 상품 컴포넌트 사용하기 위하여 변경
    private String imageUrl;
    private int categoryIdx;
    private int categoryGenreIdx;
    private LocalDateTime sDate;
    private LocalDateTime eDate;
    private int pick;
    
}
