package com.culturemoa.cultureMoaProject.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDTO {
    private String keyword;
    private Long category_idx;
    private Long categorygenre_idx;
}
