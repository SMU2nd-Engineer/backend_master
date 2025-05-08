package com.culturemoa.cultureMoaProject.product.service;

import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import com.culturemoa.cultureMoaProject.product.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;

    public List<ProductDTO> getAllProduct() {
        return productDAO.getAllProduct();
    }
}
