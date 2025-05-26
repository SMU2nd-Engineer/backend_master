package com.culturemoa.cultureMoaProject.board.repository;

import com.culturemoa.cultureMoaProject.board.dto.ContentInfoDTO;
import com.culturemoa.cultureMoaProject.board.dto.ContentsCommentDTO;
import com.culturemoa.cultureMoaProject.board.dto.ContentsCommentInfoDTO;
import com.culturemoa.cultureMoaProject.board.dto.ContentsDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContentsCommentDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public ContentsCommentDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate; }

    // 게시글 상세페이지 - 댓글 목록 가져오기
    public List<ContentsCommentInfoDTO> getComment() {
        return sqlSessionTemplate.selectList("contentsMapper.getComment");
    }

    // 게시글 상세페이지 - 댓글 등록
    public int getCommentInsert(
            ContentsCommentDTO contentsCommentDTO
    ) {
        return sqlSessionTemplate.insert("contentsMapper.getCommentInsert", contentsCommentDTO);
    }


}
