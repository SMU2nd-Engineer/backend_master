package com.culturemoa.cultureMoaProject.product.controller;

import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductImageDTO;
import com.culturemoa.cultureMoaProject.product.dto.ProductSearchDTO;
import com.culturemoa.cultureMoaProject.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping("/upload")
    public ProductDTO ProductUpload (@RequestPart("product") ProductDTO productDTO, @RequestPart("files") List<MultipartFile> files) {
        System.out.println("업로드 실행");
        try {
            String uploadDir = "C:/market_image/";
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
                productDTO.setImageUrl(imageList.get(0).getImageUrl()); // 첫번째 이미지가 썸네일이 되도록
            }
            productDTO.setFlag(false);
            productService.insertProduct(productDTO);

            return productDTO;
          } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/image/{idx}")
    public List<ProductImageDTO> imageRead(@PathVariable int product_idx){
        return productService.imageRead(product_idx);
    }


    @PostMapping("/search")
    public List<ProductDTO> searchProducts (@RequestBody ProductSearchDTO searchDTO){
//        System.out.print(productService.searchProduct());
        return productService.searchProducts(searchDTO);
    }
}

