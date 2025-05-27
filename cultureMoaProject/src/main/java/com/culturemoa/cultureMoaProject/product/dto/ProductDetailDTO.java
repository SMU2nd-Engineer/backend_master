package com.culturemoa.cultureMoaProject.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {
    private Long idx;
    private Long product_idx;
    private String image_Url;
    private Boolean flag;
    private List<ProductImageDTO> imageList;
    private ProductDTO product;
}
