package com.culturemoa.cultureMoaProject.category.service;

import com.culturemoa.cultureMoaProject.category.dto.CategoryDTO;
import com.culturemoa.cultureMoaProject.category.repository.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryDAO categoryDAO;

    @Autowired
    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public List<CategoryDTO> getCategorysByIdx(int idx){
        return categoryDAO.getCategorysByIdx(idx);
    }
}
