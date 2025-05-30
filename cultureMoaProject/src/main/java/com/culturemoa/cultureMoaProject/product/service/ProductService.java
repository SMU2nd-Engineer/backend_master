package com.culturemoa.cultureMoaProject.product.service;

import com.culturemoa.cultureMoaProject.common.util.HandleAuthentication;
import com.culturemoa.cultureMoaProject.product.dto.*;
import com.culturemoa.cultureMoaProject.product.repository.ProductDAO;
import com.culturemoa.cultureMoaProject.user.exception.DBManipulationFailException;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductDAO productDAO;
    private final UserDAO userDAO;
    private final HandleAuthentication handleAuth;
    // 생성자 패턴으로 통일하세요..
    @Autowired
    public ProductService(ProductDAO productDAO, UserDAO userDAO, HandleAuthentication handleAuth){
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.handleAuth = handleAuth;
    }

    // 상품 전체 정보 불러오기
    public List<ProductDTO> getAllProduct() {
        return productDAO.getAllProduct();
    }

    // 상품 idx에 맞는 해당 디테일 정보 불러오기
    public ProductDTO getProductByIdx(long idx) {

        ProductDTO product = productDAO.getProductByIdx(idx);
        List<ProductImageDTO> images = productDAO.getProductImagesByProductIdx(idx);
        product.setImageList(images);
        if( images != null && !images.isEmpty()) {
            product.setImage_Url(images.get(0).getImage_Url());
        }
        return product;
    }

    // 상품 등록
    @Transactional
    public void insertProduct(ProductDTO productDTO) {
        String userid = handleAuth.getUserIdByAuth();
        int user_idx = userDAO.getUserIdx(userid);
        productDTO.setUser_idx((long) user_idx);

        if(productDTO.getImageList() == null || productDTO.getImageList().isEmpty()) {
            throw new IllegalArgumentException("상품 이미지를 포함해주세요");
        }

        productDAO.insertProduct(productDTO);
        if (productDTO.getIdx() == 0) {
            throw new RuntimeException("Product insert failed.");
        }
        if (productDTO.getImageList() != null) {
            for (ProductImageDTO imageDTO : productDTO.getImageList()) {
                ProductDetailDTO detail = new ProductDetailDTO();
                detail.setProduct_idx(productDTO.getIdx());
                detail.setImage_Url(imageDTO.getImage_Url());
                detail.setFlag(imageDTO.getFlag());

                productDAO.insertProductDetail(detail);
            }
        }
    }

    // 이미지 저장
    public String saveImage(MultipartFile image, String uploadDir) throws IOException {
        String originalFilename = image.getOriginalFilename();
        String extension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        
        // 파일 이름 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS");
        String timestamp = LocalTime.now().toString().replace(":", "-");
        String imageUrl = UUID.randomUUID() + "_" + timestamp + extension;  // UUID 동일 파일명 방지
        // 파일 경로
        String imagePath = uploadDir + imageUrl;
        // DB 저장할
        String dbImagePath = "/upload_img/" + imageUrl;

        Path path = Paths.get(imagePath); // Path 객체 생성
        Files.createDirectories(path.getParent());
        Files.write(path,image.getBytes());

        return dbImagePath;
    }

    // 상품 검색
    public List<ProductDTO> searchProducts(ProductSearchDTO searchDTO) {
        String userid = handleAuth.getUserIdByAuth();
        int user_idx = userDAO.getUserIdx(userid);
        return productDAO.searchProducts(searchDTO);
    }

    // 상세 이미지 불러오기
    public List<ProductImageDTO> imageRead(long product_idx){
        return productDAO.getProductImagesByProductIdx(product_idx);
    }

    // 상품 수정
    public void updateProduct(ProductDTO productDTO, ProductImageDTO productImageDTO) {

        try {
            productDAO.updateProduct(productDTO);

            if(productImageDTO != null && productImageDTO.getImage_Url() != null) {
                productDAO.updateProduct(productDTO);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("상품 수정에 실패했습니다.", e);
        }
    }

    @Transactional
    public void updateProductImages(ProductDTO productDTO, List<ProductImageDTO> imageList) {
        Long idx = productDTO.getIdx();

        productDAO.updateProduct(productDTO);

        if (imageList != null && !imageList.isEmpty()) {
            // 기존 이미지를 flag 0으로 업데이트
            for (ProductImageDTO imageDTO : imageList) {
                ProductDetailDTO detail = new ProductDetailDTO();
                detail.setProduct_idx(idx);
                detail.setImage_Url(imageDTO.getImage_Url());
                detail.setFlag(imageDTO.getFlag());
                productDAO.insertProductDetail(detail);
            }
        }

    }

    // 상품 삭제
    public void deleteProduct(Long idx){

        LocalDateTime localDateTime = LocalDateTime.now();
        ProductDeleteDTO deleteDTO = new ProductDeleteDTO(idx, localDateTime);

        int productDeleteProcess = productDAO.deleteProduct(deleteDTO);

        if(productDeleteProcess == 0) {
            throw new DBManipulationFailException();
        }
    }




}
