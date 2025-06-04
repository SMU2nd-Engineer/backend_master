package com.culturemoa.cultureMoaProject.product.service;

import com.culturemoa.cultureMoaProject.board.dto.ContentsDetailImageDTO;
import com.culturemoa.cultureMoaProject.common.service.S3Service;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductDAO productDAO;
    private final UserDAO userDAO;
    private final HandleAuthentication handleAuth;
    private final S3Service s3Service;

    @Autowired
    public ProductService(ProductDAO productDAO, UserDAO userDAO, HandleAuthentication handleAuth, S3Service s3Service) {
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.handleAuth = handleAuth;
        this.s3Service = s3Service;
    }

    // 상품 전체 정보 불러오기
    public List<ProductDTO> getProducts(Long lastId, long size) {
        String userid = handleAuth.getUserIdByAuth();
        long userIdx = userDAO.getUserIdx(userid);
        return productDAO.getProducts(userIdx, lastId, size);
    }

    // 상품 idx에 맞는 해당 디테일 정보 불러오기
    public ProductDTO getProductByIdx(long idx) {
        String userid = handleAuth.getUserIdByAuth();
        long userIdx = userDAO.getUserIdx(userid);

        ProductDTO product = productDAO.getProductByIdx(userIdx, idx);
        List<ProductImageDTO> images = productDAO.getProductImagesByProductIdx(idx);
        product.setImageList(images);
        if( images != null && !images.isEmpty()) {
            product.setImage_Url(images.get(0).getImage_Url());
        }
        return product;
    }

    // 상품 등록
    @Transactional
    public void insertProduct(ProductDTO productDTO, List<MultipartFile> files) throws IOException {
        String userid = handleAuth.getUserIdByAuth();
        int user_idx = userDAO.getUserIdx(userid);
        String uploadDir = "product/";
        List<ProductImageDTO> imageList = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String imageUrl = s3Service.uploadImageToBucketPath(file, uploadDir);

                ProductImageDTO imageDTO = new ProductImageDTO();

                imageDTO.setImage_Url(imageUrl);
                imageDTO.setFlag(true); // 사용중인 사진 true
                imageList.add(imageDTO);
            }
        }
        if (!imageList.isEmpty()) {
            productDTO.setImageList(imageList);
            productDTO.setImage_Url(imageList.get(0).getImage_Url()); // 첫번째 사진이 image_Url
        }

        productDTO.setFlag(false); // 상품 판매중 (true 판매완)

        productDTO.setUser_idx((long) user_idx);

        productDAO.insertProduct(productDTO);

        if (productDTO.getIdx() == 0) {
            throw new RuntimeException("Product insert failed.");
        }
        if (productDTO.getImageList() != null) {
            insertProductImage(productDTO.getIdx(), productDTO.getImageList());
        }
    }

    // 상품 검색
    public List<ProductDTO> searchProducts(ProductSearchDTO searchDTO) {
        String userid = handleAuth.getUserIdByAuth();
        searchDTO.setUserIdx(Long.valueOf(userDAO.getUserIdx(userid)));
        return productDAO.searchProducts(searchDTO);
    }

    // 상세 이미지 불러오기
    public List<ProductImageDTO> getProductImagesByProductIdx(long productIdx){
        return productDAO.getProductImagesByProductIdx(productIdx);
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
    public void updateProductImages(
            Long idx,
            ProductDTO productDTO,
            List<MultipartFile> files,
            List<String> currentUrls) throws IOException {
        String uploadDir = "product/";
        productDTO.setIdx(idx);

        List<ProductImageDTO> imageList = new ArrayList<>();
        List<ProductImageDTO> toDelete;
        List<ProductImageDTO> savedUrls = productDAO.getProductImagesByProductIdx(idx);

        // 새 이미지가 업로드 되고 기존 이미지 false
        if (currentUrls != null && !currentUrls.isEmpty()) {

            // 새로 업로드 된 이미지 저장
            for (String current : currentUrls) {
                if (current.startsWith("https://")){
                    ProductImageDTO newImage = new ProductImageDTO();
                    newImage.setImage_Url(current);
                    newImage.setFlag(true); // 사용할 이미지
                    imageList.add(newImage);
                }
                else  {
                    MultipartFile file = files.get(0);
                    String imageUrl = s3Service.uploadImageToBucketPath(file, uploadDir);
                    ProductImageDTO newImage = new ProductImageDTO();
                    newImage.setImage_Url(imageUrl);
                    newImage.setFlag(true); // 사용할 이미지
                    imageList.add(newImage);
                    files.remove(0);
                }
            }
        }

        // 기존 s3 이미지 삭제
        List<String> incomingUrls = currentUrls.stream()
                .filter(url -> url.startsWith("http"))
                .toList();
        toDelete = savedUrls.stream()
                .filter(url -> !incomingUrls.contains(url.getImage_Url()))
                .toList();

        for(ProductImageDTO detailImageDTO : toDelete){
            s3Service.deleteByUrl(detailImageDTO.getImage_Url());
        }

        if (!imageList.isEmpty()) {
            productDTO.setImageList(imageList);
            productDTO.setImage_Url(imageList.get(0).getImage_Url()); // 첫번째 사진이 image_Url
        }

        productDAO.deleteImageByIdx(idx);
        productDAO.updateProduct(productDTO);

        if (!imageList.isEmpty()) {
            insertProductImage(idx, imageList);
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

    public void insertProductImage(Long idx, List<ProductImageDTO> imageList){
        for (ProductImageDTO imageDTO : imageList) {
            ProductDetailDTO detail = new ProductDetailDTO();
            detail.setProduct_idx(idx);
            detail.setImage_Url(imageDTO.getImage_Url());
            detail.setFlag(imageDTO.getFlag());
            productDAO.insertProductDetail(detail);
        }
    }


}
