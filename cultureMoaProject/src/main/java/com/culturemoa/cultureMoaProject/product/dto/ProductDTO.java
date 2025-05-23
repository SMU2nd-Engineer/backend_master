package com.culturemoa.cultureMoaProject.product.dto;

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
public class ProductDTO {
    private Long idx;
    private Long category_idx;
    private Long categorygenre_idx;
    private String title;
    private LocalDateTime sDate =LocalDateTime.now();
    private LocalDateTime eDate;
    private Long user_idx;
    private Long price;
    private boolean flag;
    private String imageUrl;
    private String content;
    private List<ProductImageDTO> imageList;

}

