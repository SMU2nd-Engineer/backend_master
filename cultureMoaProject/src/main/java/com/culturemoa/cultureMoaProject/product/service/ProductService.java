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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        ProductDTO product = productDAO.getProductByIdx(idx);
        List<ProductImageDTO> images = productDAO.imageRead(idx);
        product.setImageList(images);
        if( images != null && !images.isEmpty()) {
            product.setImage_Url(images.get(0).getImage_Url());
        }


        return product;
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
                detail.setImage_Url(imageDTO.getImage_Url());
                detail.setFlag(imageDTO.isFlag());

                productDAO.insertProductDetail(detail);
            }
        }
    }

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

    public List<ProductDTO> searchProducts(ProductSearchDTO searchDTO) {
        return productDAO.searchProducts(searchDTO);
    }
    public List<ProductImageDTO> imageRead(int product_idx){
        return productDAO.imageRead(product_idx);
    }


}
