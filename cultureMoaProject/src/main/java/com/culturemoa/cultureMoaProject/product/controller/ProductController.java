package com.culturemoa.cultureMoaProject.product.controller;

import com.culturemoa.cultureMoaProject.common.service.S3Service;
import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductImageDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductSearchDTO;
import com.culturemoa.cultureMoaProject.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    // 생성자 패턴으로 Autowired 사용해주세요
    private final ProductService productService;
    private final S3Service s3Service;

    @Autowired
    public ProductController(S3Service s3Service, ProductService productService) {
        this.s3Service = s3Service;
        this.productService = productService;
    }

    // 전체 목록 불러오기
    @GetMapping("/list")
    public List<ProductDTO> getProducts(@RequestParam(required = false) Long lastId, @RequestParam(defaultValue = "20") Long size) {
        return productService.getProducts(lastId, size);

    }

    // 해당 상품 불러오기
    @GetMapping("/detail/{idx}")
    public ProductDTO getProductByIdx(@PathVariable long idx) {
        return productService.getProductByIdx(idx);
    }

    @PostMapping("/upload")
    public ProductDTO uploadProduct(@RequestPart("product") ProductDTO productDTO, @RequestPart("files") List<MultipartFile> files) {
        try {
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
            productService.insertProduct(productDTO);

            return productDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // 검색
    @PostMapping("/search")
    public List<ProductDTO> searchProducts(@RequestBody ProductSearchDTO searchDTO) {
        return productService.searchProducts(searchDTO);
    }

    // 수정
    @PutMapping("/detail/{idx}")
    public ResponseEntity<?> updateProductImages(
            @PathVariable Long idx,
            @RequestPart("product") ProductDTO productDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        try {
            String uploadDir = "product/";
            productDTO.setIdx(idx);

            List<ProductImageDTO> imageList = new ArrayList<>();

            // 새 이미지가 업로드 되고 기존 이미지 false
            if (files != null && !files.isEmpty()) {
                List<ProductImageDTO> existingImages = productService.imageRead(idx);

                for (ProductImageDTO oldImage : existingImages) {
                    oldImage.setFlag(false);
                    imageList.add(oldImage);
                }

                // 새로 업로드 된 이미지 저장
                for (MultipartFile file : files) {
                    if (files != null && !file.isEmpty()) {
                        String imageUrl = s3Service.uploadImageToBucketPath(file, uploadDir);
                        ProductImageDTO newImage = new ProductImageDTO();
                        newImage.setImage_Url(imageUrl);
                        newImage.setFlag(true); // 사용할 이미지
                        imageList.add(0, newImage);
                    }
                }
            }
            productDTO.setImageList(imageList);
            productService.updateProductImages(productDTO, productDTO.getImageList());

            return ResponseEntity.ok(productDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("update failed");
        }
    }

    // 상품 삭제
    @PutMapping("/delete/{idx}")
    public ResponseEntity<?> productDelete(@PathVariable long idx){
        productService.deleteProduct(idx);
        return ResponseEntity.ok("삭제 성공");
    }

}




