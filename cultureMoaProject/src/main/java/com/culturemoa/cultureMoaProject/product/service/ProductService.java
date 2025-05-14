package com.culturemoa.cultureMoaProject.product.service;

import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductDetailDTO;
import com.culturemoa.cultureMoaProject.product.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductDAO productDAO;

    @Autowired
    public ProductService(ProductDAO productDAO){
        this.productDAO = productDAO;
    }

    public List<ProductDTO> getAllProduct() {
        return productDAO.getAllProduct();
    }

    public ProductDTO getProductByIdx(int idx) {
        return productDAO.getProductByIdx(idx);
    }

    @Transactional
    public void insertProduct(ProductDTO productDTO) {

        productDAO.insertProduct(productDTO);
        if (productDTO.getIdx() == 0) {
            throw new RuntimeException("Product insert failed.");
        }
        ProductDetailDTO detail = new ProductDetailDTO();
            detail.setProduct_idx(productDTO.getIdx());
            detail.setContent(productDTO.getContent());
            detail.setImageUrl(productDTO.getImageUrl());

        productDAO.insertProductDetail(detail);
    }




}
