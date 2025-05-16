package com.culturemoa.cultureMoaProject.category.repository;

import com.culturemoa.cultureMoaProject.category.dto.CategoryDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public CategoryDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<CategoryDTO> getCategorysByIdx(int idx) {
        return sqlSessionTemplate.selectList("categoryMapper.getCategorysByIdx", idx);
    }
}
