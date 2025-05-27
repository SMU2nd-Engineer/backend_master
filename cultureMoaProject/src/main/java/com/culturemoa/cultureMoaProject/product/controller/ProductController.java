package com.culturemoa.cultureMoaProject.product.controller;

import com.culturemoa.cultureMoaProject.common.util.HandleAuthentication;
import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductImageDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductSearchDTO;
import com.culturemoa.cultureMoaProject.product.service.ProductService;
import com.culturemoa.cultureMoaProject.user.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private HandleAuthentication handleAuth;

    @GetMapping("/list")
    public List<ProductDTO> product() {
        System.out.println(productService.getAllProduct());

        return productService.getAllProduct();

    }

    @GetMapping("/detail/{idx}")
    public ProductDTO getProductByIdx(@PathVariable long idx) {

        System.out.println("################## idx: " + idx);
        return productService.getProductByIdx(idx);
    }

    @GetMapping("/upload_img/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("C:/upload_img").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // 파일 확장자에 따라 Content-Type 지정
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // 기본값
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (
                MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/upload")
    public ProductDTO ProductUpload(@RequestPart("product") ProductDTO productDTO, @RequestPart("files") List<MultipartFile> files) {
        System.out.println("업로드 실행");
        try {
            String uploadDir = "C:/upload_img/";
            List<ProductImageDTO> imageList = new ArrayList<>();

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String imageUrl = productService.saveImage(file, uploadDir);

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


    @PostMapping("/search")
    public List<ProductDTO> searchProducts(@RequestBody ProductSearchDTO searchDTO) {
//        System.out.print(productService.searchProduct());
        return productService.searchProducts(searchDTO);
    }


    @PutMapping("/edit/{idx}")
    public ResponseEntity<String> updateProductImages(
            @PathVariable Long idx,
            @RequestPart("product") ProductDTO productDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        System.out.println("✅ [Controller] 수정 요청 도착 - idx: " + idx);
        try {
            String uploadDir = "C:/upload_img/";
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
                        String imageUrl = productService.saveImage(file, uploadDir);
                        ProductImageDTO newImage = new ProductImageDTO();
                        newImage.setImage_Url(imageUrl);
                        newImage.setFlag(true); // 사용할 이미지
                        imageList.add(newImage);
                    }
                }
            }
            productDTO.setImageList(imageList);
            productService.updateProductImages(productDTO, productDTO.getImageList());

            return ResponseEntity.ok("update success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("update failed");
        }
    }



}




