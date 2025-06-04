package com.culturemoa.cultureMoaProject.product.controller;

import com.culturemoa.cultureMoaProject.common.service.S3Service;
import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductSearchDTO;
import com.culturemoa.cultureMoaProject.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
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

            productService.insertProduct(productDTO, files);

            return productDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // 검색
    @PostMapping("/search")
    public List<ProductDTO> searchProducts(@RequestBody ProductSearchDTO searchDTO,
                                           @RequestParam(required = false) Long lastId,
                                           @RequestParam(defaultValue = "20") Long size) {
        if (lastId != null) searchDTO.setLastId(lastId);
        searchDTO.setSize(size);

        return productService.searchProducts(searchDTO);
    }

    // 수정
    @PutMapping("/detail/{idx}")
    public ResponseEntity<?> updateProductImages(
            @PathVariable Long idx,
            @RequestPart("product") ProductDTO productDTO,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            @RequestParam(value = "current", required = false) List<String> currentUrls
    ) {
        try {
            productService.updateProductImages(idx, productDTO, files, currentUrls);

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




