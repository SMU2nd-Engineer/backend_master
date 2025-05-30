package com.culturemoa.cultureMoaProject.search.service;

import com.culturemoa.cultureMoaProject.board.dto.ContentInfoDTO;
import com.culturemoa.cultureMoaProject.board.service.ContentsService;
import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductSearchDTO;
import com.culturemoa.cultureMoaProject.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ProductService productService;
    private final ContentsService contentsService;

    public Map<String, Object> searchAll(String keyword) {
        Map<String, Object> result = new HashMap<>();

        // 상품 검색
        ProductSearchDTO productSearchDTO = new ProductSearchDTO();
        productSearchDTO.setKeyword(keyword);
        List<ProductDTO> products = productService.searchProducts(productSearchDTO);
        result.put("products", products);

        // 게시글 검색
        Long category_idx = null;
        String searchType = null;
        List<ContentInfoDTO> contents = contentsService.getContentSearchs(category_idx, keyword, searchType);
        result.put("contents", contents);

        return  result;

    }
}
