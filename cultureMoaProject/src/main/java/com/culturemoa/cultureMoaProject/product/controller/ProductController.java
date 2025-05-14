package com.culturemoa.cultureMoaProject.product.controller;

import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import com.culturemoa.cultureMoaProject.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@CrossOrigin(origins = "*")
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

    @PostMapping("/upload")
    public ProductDTO ProductUpload (@RequestBody ProductDTO productDTO) {
//        if(imageUrl == null || imageUrl.isEmpty()) {
//            System.out.println("이미지가 업로드 되지 않았습니다.");
//        } else {
//            System.out.println("이미지가 저장되었습니다.");
//        }
        System.out.println("이미지가 저장되었습니다.");
        productDTO.setFlag(false);
        productService.insertProduct(productDTO);
        return productDTO;
    }
}

