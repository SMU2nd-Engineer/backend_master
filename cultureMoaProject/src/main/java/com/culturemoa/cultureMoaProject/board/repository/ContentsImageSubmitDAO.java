package com.culturemoa.cultureMoaProject.board.repository;

import com.culturemoa.cultureMoaProject.board.dto.ContentsImageSubmitDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ContentsImageSubmitDAO {
    // 생성자 패턴인데 왜 Autowired가 변수 선언부에 있나요 생성자로 옮기세요
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


