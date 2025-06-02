package com.culturemoa.cultureMoaProject.board.repository;

import com.culturemoa.cultureMoaProject.board.dto.*;
import com.culturemoa.cultureMoaProject.product.dto.ProductImageDTO;
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
    public List<ContentInfoDTO> getContentsSearchCriteria(Map<String, Object> searchMap) {
        return sqlSessionTemplate.selectList("contentsMapper.getContentsSearchCriteria", searchMap);
    }

    // 게시글 등록 페이지(카테고리, 제목, 글 내용(텍스트))
    public int getContentInsert(
            ContentsDTO contentsDTO
    ) {
        return sqlSessionTemplate.insert("contentsMapper.getContentInsert", contentsDTO);
    }

    // 게시글 등록 페이지(이미지 등록)
    public void getContentsImageInsert (ContentsDetailImageDTO detailImageDTO) {
        sqlSessionTemplate.insert("contentsMapper.getContentsImageInsert", detailImageDTO);
    }

    // 게시글 상세페이지(이미지 수정)
    public int postModifyContentsImage ( ContentsDetailImageModifyDTO imageModifyDTO) {
           return sqlSessionTemplate.update("contentsMapper.postModifyContentsImage", imageModifyDTO);

    }

    // 게시글 상세 페이지(이미지 정보 불러오기)
    public List<ContentsDetailImageDTO> boardImageRead(Long contents_idx) {
        return sqlSessionTemplate.selectList("contentsMapper.boardImageRead", contents_idx );
    }

    // 게시글 상세페이지 정보 불러오기
    public ContentInfoDTO getContentsParticular (
            Long idx
    ) {
        return sqlSessionTemplate.selectOne("contentsMapper.getContentsParticular ", idx);
    }

    // 게시글 상세페이지 게시글 정보 수정
    public void updateContents(ContentsDetailImageModifyDTO imageModifyDTO) {
        int updateContent = sqlSessionTemplate.update("contentsMapper.updateContents", imageModifyDTO);
        if (updateContent == 0) {
            throw new RuntimeException("수정할 게시글이 없습니다.");
        }
    }


    // 게시글 상세페이지 게시글 삭제
    public int deleteContents(ContentsDeleteInfoDTO contentsDeleteInfoDTO) {
        return  sqlSessionTemplate.update("contentsMapper.deleteContents", contentsDeleteInfoDTO);
    }


    // ContentsService.Map을 받아서 조건에 맞는 쿼리 실행 : 검색된 게시글 조회하여 불러옴
    public List<ContentInfoDTO> getContentByTitleAndContent(String keyword) {
        return sqlSessionTemplate.selectList("contentsMapper.getContentByTitleAndContent", keyword);
    }

}
