package com.culturemoa.cultureMoaProject.search.service;

import com.culturemoa.cultureMoaProject.board.dto.ContentInfoDTO;
import com.culturemoa.cultureMoaProject.board.repository.ContentsDAO;
import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductSearchDTO;
import com.culturemoa.cultureMoaProject.product.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    private final ProductDAO productDAO;
    private final ContentsDAO contentsDAO;

    @Autowired
    public SearchService(ProductDAO productDAO, ContentsDAO contentsDAO) {
        this.productDAO = productDAO;
        this.contentsDAO = contentsDAO;
    }

    public Map<String, Object> searchAllByKeyword(String keyword) {
        Map<String, Object> result = new HashMap<>();

        // 상품 검색
        ProductSearchDTO productSearchDTO = new ProductSearchDTO();
        productSearchDTO.setKeyword(keyword);
        List<ProductDTO> products = productDAO.searchProducts(productSearchDTO);
        result.put("products", products);

        // 게시글 검색
        List<ContentInfoDTO> contents = contentsDAO.getContentByTitleAndContent(keyword);
        result.put("contents", contents);

        return  result;

    }
}