package com.culturemoa.cultureMoaProject.product.controller;

import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductImageDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductSearchDTO;
import com.culturemoa.cultureMoaProject.product.service.ProductService;
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

    @GetMapping("/list")
    public List<ProductDTO> product() {
        System.out.println(productService.getAllProduct());

        return productService.getAllProduct();

    }

    @GetMapping("/detail/{idx}")
    public ProductDTO getProductByIdx(@PathVariable int idx) {
        System.out.println("################## idx: " + idx);
        return productService.getProductByIdx(idx);
    }

    @GetMapping("/upload_img/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename)  {
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
    public ProductDTO ProductUpload (@RequestPart("product") ProductDTO productDTO, @RequestPart("files") List<MultipartFile> files) {
        System.out.println("업로드 실행");
        try {
            String uploadDir = "C:/upload_img/";
            List<ProductImageDTO> imageList = new ArrayList<>();

            for (int i = 0; i< files.size(); i++) {
                MultipartFile file = files.get(i);
                if (!file.isEmpty()) {
                    String imageUrl = productService.saveImage(file, uploadDir);
                    boolean flag = (i == 0 ); // 첫번째 이미지 flag true
                    imageList.add(new ProductImageDTO(imageUrl, flag));
                }
            }

            productDTO.setImageList(imageList);

            if(!imageList.isEmpty()){
                productDTO.setImage_Url(imageList.get(0).getImage_Url()); // 첫번째 이미지가 썸네일이 되도록
            }
            productDTO.setFlag(false);
            productService.insertProduct(productDTO);

            return productDTO;
          } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/search")
    public List<ProductDTO> searchProducts (@RequestBody ProductSearchDTO searchDTO){
//        System.out.print(productService.searchProduct());
        return productService.searchProducts(searchDTO);
    }

    @PutMapping("/edit/{idx}")
    public ResponseEntity<String> updateProduct(
            @PathVariable Long idx,
            @RequestPart("product") ProductDTO productDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
        try {
            String uploadDir = "C:/upload_img/";
            productDTO.setIdx(idx);

            List<ProductImageDTO> imageList = new ArrayList<>();

            if (files != null && !files.isEmpty()) {
                for (int i = 0; i < files.size(); i++) {
                    MultipartFile file = files.get(i);
                    if (!file.isEmpty()) {
                        String imageUrl = productService.saveImage(file, uploadDir);
                        boolean flag = (i == 0); // 첫번째 이미지는 대표 이미지 (flag true)
                        imageList.add(new ProductImageDTO(imageUrl, flag));
                    }
                }
                productDTO.setImageList(imageList);

                if (!imageList.isEmpty()) {
                    productDTO.setImage_Url(imageList.get(0).getImage_Url());
                }
            }
            productService.updateProductWithImages(productDTO, imageList);

            return ResponseEntity.ok("update success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("update failed");
        }
    }
    }




