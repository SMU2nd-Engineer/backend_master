package com.culturemoa.cultureMoaProject.board.repository;

import com.culturemoa.cultureMoaProject.board.dto.ContentsCommentDTO;
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

    public List<ContentsCommentDTO> getAllContentsComment() {
        return sqlSessionTemplate.selectList("contentsCommentMapper.getAllContentsComment");
    }
}
