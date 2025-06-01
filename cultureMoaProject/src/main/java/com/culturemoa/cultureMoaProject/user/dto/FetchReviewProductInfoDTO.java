package com.culturemoa.cultureMoaProject.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 리뷰 수정 페이지의 사용할 상품 정보를 가져올 dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetchReviewProductInfoDTO {
    @JsonProperty("idx")
    private int productIdx;
    private String title;
    private Long price;
    @JsonProperty("image_Url")
    private String imageUrl;
    private int tradeType;
    @JsonProperty("user_idx")
    private int userIdx;

}
