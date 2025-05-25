package com.culturemoa.cultureMoaProject.product.service;

import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductDetailDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductImageDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductSearchDTO;
import com.culturemoa.cultureMoaProject.product.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
        if (productDTO.getImageList() != null) {
            for (ProductImageDTO imageDTO : productDTO.getImageList()) {
                ProductDetailDTO detail = new ProductDetailDTO();
                detail.setProduct_idx(productDTO.getIdx());
                detail.setImageUrl(imageDTO.getImageUrl());

                productDAO.insertProductDetail(detail);
            }
        }
    }

    public List<ProductDTO> searchProducts(ProductSearchDTO searchDTO) {
        return productDAO.searchProducts(searchDTO);
    }

    public String saveImage(MultipartFile image, String uploadDir) throws IOException {
        // 파일 이름 생성
        String imageUrl = UUID.randomUUID() + "_" + image.getOriginalFilename();    // UUID 동일 파일명 방지
        // 파일 경로
        String imagePath = uploadDir + imageUrl;
        // DB 저장할
        String dbImagePath = "/upload/" + imageUrl;

        Path path = Paths.get(imagePath); // Path 객체 생성
        Files.createDirectories(path.getParent());
        Files.write(path,image.getBytes());

        return dbImagePath;
    }

    public List<ProductImageDTO> imageRead(int product_idx){
        return productDAO.imageRead(product_idx);};

}
