package com.culturemoa.cultureMoaProject.board.repository;

import com.culturemoa.cultureMoaProject.board.dto.ContentsDTO;
import com.culturemoa.cultureMoaProject.board.dto.ContentInfoDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContentsDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public ContentsDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<ContentsDTO> getAllContents() {
        return sqlSessionTemplate.selectList("contentsMapper.getAllContents");
    }

    public List<ContentInfoDTO> getContentInfos() {
        return sqlSessionTemplate.selectList("contentsMapper.getContentInfos");
    }
}
