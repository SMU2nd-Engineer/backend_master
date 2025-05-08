package com.culturemoa.cultureMoaProject.product.controller;

import com.culturemoa.cultureMoaProject.product.dto.ProductDTO;
import com.culturemoa.cultureMoaProject.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
