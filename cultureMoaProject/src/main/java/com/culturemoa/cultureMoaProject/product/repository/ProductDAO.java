package com.culturemoa.cultureMoaProject.product.repository;

import com.culturemoa.cultureMoaProject.product.dto.*;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public ProductDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

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

    public List<ProductDTO> searchProducts(ProductSearchDTO searchDTO) {
        return sqlSessionTemplate.selectList("productMapper.searchProducts", searchDTO);
    }

    public List<ProductImageDTO> imageRead(int product_idx) {
        return sqlSessionTemplate.selectList("productMapper.imageRead", product_idx );
    }

    public void updateProduct(ProductDTO productDTO) {
        int updatedCount = sqlSessionTemplate.update("productMapper.updateProductDetail", productDTO);
        if (updatedCount == 0) {
            throw new RuntimeException("수정할 상품이 없습니다.");
        }
    }

    public void deleteProductImages(Long idx) {
    }


}
