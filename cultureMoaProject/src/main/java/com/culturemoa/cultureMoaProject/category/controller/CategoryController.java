package com.culturemoa.cultureMoaProject.category.controller;

import com.culturemoa.cultureMoaProject.category.dto.CategoryDTO;
import com.culturemoa.cultureMoaProject.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{idx}")
    public List<CategoryDTO> getCategoryByIdx(@PathVariable int idx){
        return categoryService.getCategorysByIdx(idx);
    }
}
