package com.culturemoa.cultureMoaProject.board.repository;

import com.culturemoa.cultureMoaProject.board.dto.ContentsDTO;
import com.culturemoa.cultureMoaProject.board.dto.ContentInfoDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
        System.out.println("여기까지 실행됩니다.");
        return sqlSessionTemplate.selectList("contentsMapper.getContentInfos");
    }

    // ContentsService.Map을 받아서 조건에 맞는 쿼리 실행 : 검색된 게시글 조회하여 불러옴
    public List<ContentInfoDTO> getContentSearchs(Map<String, Object> searchMap) {
        return sqlSessionTemplate.selectList("contentsMapper.getContentSearchs", searchMap);
    }

    // ContentsService.Map을 받아서 조건에 맞는 쿼리 실행 : 게시글 등록하여 불러옴
    public List<ContentInfoDTO> getContentInsert() {
        return sqlSessionTemplate.selectList("contentsMapper.getContentInsert");
    }

}
