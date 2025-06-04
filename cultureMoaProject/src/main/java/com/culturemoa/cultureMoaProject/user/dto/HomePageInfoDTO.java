package com.culturemoa.cultureMoaProject.user.dto;

import com.culturemoa.cultureMoaProject.board.dto.ContentInfoDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 홈페이지에 필요한 정보를 전달할 dto
 * latestProducts : 최신 상품 10개가 담길 ProductDTO 리스트 변수
 * latestContent : 최신 게시글 10개가 담길  ContentInfoDTO 리스트 변수
 * userInfo : 유저 닉네임, 유저idx, 유저 이름 정보가 담길 dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomePageInfoDTO {
    private List<ProductDTO> latestProducts;
    private List<ContentInfoDTO> latestContents;
}
