package com.culturemoa.cultureMoaProject.product.repository;

import com.culturemoa.cultureMoaProject.product.dto.*;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public ProductDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<ProductDTO> getProducts(long userIdx, Long lastId, long size) {
        Map<String, Object> params = new HashMap<>();
        params.put("userIdx", userIdx);
        params.put("lastId", lastId);
        params.put("size", size);
        return sqlSessionTemplate.selectList("productMapper.getAllProduct", params);
    }

    public ProductDTO getProductByIdx(long userIdx, long idx) {
        Map<String, Object> params = new HashMap<>();
        params.put("userIdx", userIdx);
        params.put("idx", idx);
        return sqlSessionTemplate.selectOne("productMapper.getProductByIdx", params);
    }

    public void insertProduct(ProductDTO product){
        sqlSessionTemplate.insert("productMapper.insertProduct", product);
    }
    public void insertProductDetail(ProductDetailDTO productdetail){
        sqlSessionTemplate.insert("productMapper.insertProductDetail", productdetail);
    }

    public List<ProductDTO> searchProducts(ProductSearchDTO searchDTO) {
        return sqlSessionTemplate.selectList("productMapper.searchProducts", searchDTO);
    }

    public List<ProductImageDTO> getProductImagesByProductIdx(long productIdx) {
        return sqlSessionTemplate.selectList("productMapper.getProductImagesByProductIdx", productIdx );
    }

    public void updateProduct(ProductDTO productDTO) {
        int updatedCount = sqlSessionTemplate.update("productMapper.updateProduct", productDTO);
        if (updatedCount == 0) {
            throw new RuntimeException("수정할 상품이 없습니다.");
        }
    }

    public int deleteProduct(ProductDeleteDTO productDeleteDTO){
        return sqlSessionTemplate.update("productMapper.deleteProduct", productDeleteDTO);
    }

    public int deleteImageByIdx(Long idx){
        return sqlSessionTemplate.delete("productMapper.deleteImageByIdx", idx);
    }

}
