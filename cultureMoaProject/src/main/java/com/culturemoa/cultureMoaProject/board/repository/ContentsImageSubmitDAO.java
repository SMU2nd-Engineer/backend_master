package com.culturemoa.cultureMoaProject.board.repository;

import com.culturemoa.cultureMoaProject.board.dto.ContentsImageSubmitDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ContentsImageSubmitDAO {
    @Autowired
    private final SqlSessionTemplate sqlSessionTemplate;

    public ContentsImageSubmitDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate; }

    // 게시글 등록페이지 - 이미지 등록
    public int getImageInsert (
            ContentsImageSubmitDTO contentsImageSubmitDTO
    ) {
        return sqlSessionTemplate.insert("contentsMapper.getImageInsert", contentsImageSubmitDTO);
    }


    }


