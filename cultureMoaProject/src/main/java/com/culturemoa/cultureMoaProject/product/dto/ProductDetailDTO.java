package com.culturemoa.cultureMoaProject.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {
    private Long idx;
    private Long product_idx;
    private String content;
    private String imageUrl;
}
