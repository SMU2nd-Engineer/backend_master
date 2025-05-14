package com.culturemoa.cultureMoaProject.product.repository;

import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductDetailDTO;
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

    public ProductDTO getProductByIdx(int idx) {
        return sqlSessionTemplate.selectOne("productMapper.getProductByIdx", idx);
    }

    public void insertProduct(ProductDTO product){
        sqlSessionTemplate.insert("productMapper.insertProduct", product);
    }
    public void insertProductDetail(ProductDetailDTO productdetail){
        sqlSessionTemplate.insert("productMapper.insertProductDetail", productdetail);
    }
}
