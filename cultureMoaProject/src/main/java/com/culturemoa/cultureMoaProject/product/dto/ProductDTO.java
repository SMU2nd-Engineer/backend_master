package com.culturemoa.cultureMoaProject.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private Long idx;
    private Long category_idx;
    private Long categorygenre_idx;
    private String title;
    private LocalDateTime sDate =LocalDateTime.now();
    private LocalDateTime eDate;
    private Long user_idx;
    private Long price;
    private Boolean flag;
    private String nickName;

    @JsonProperty("image_Url")
    private String image_Url;

    private String content;

    @JsonProperty("imageList")
    private List<ProductImageDTO> imageList;

}

