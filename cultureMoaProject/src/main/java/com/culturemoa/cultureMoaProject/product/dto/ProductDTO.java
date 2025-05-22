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
    private String userId;
    private Long price;
    private boolean flag;
    private String imageUrl;
    private String content;
    private List<ProductImageDTO> imageList;


//    private ProductDetailDTO detail;

//    public ProductDTO(Long idx, Long category_idx, String title, LocalDateTime sDate, LocalDateTime eDate, String useId, Long price, Long flag, String imageUrl, String content) {
//        this.idx = idx;
//        this.category_idx = category_idx;
//        this.title = title;
//        this.sDate = sDate = LocalDateTime.now();
//        this.eDate = eDate;
//        this.useId = useId;
//        this.price = price;
//        this.flag = flag;
//        this.imageUrl = imageUrl;
//        this.content = content;
//    }
//    public ProductDTO(){
//        this.sDate = LocalDateTime.now();
//    }
}

