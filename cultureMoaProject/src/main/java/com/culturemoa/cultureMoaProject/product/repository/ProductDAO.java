package com.culturemoa.cultureMoaProject.product.repository;

import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public List<ProductDTO> getAllProduct() {
        return sqlSessionTemplate.selectList("productMapper.getAllProduct");
    }
}
