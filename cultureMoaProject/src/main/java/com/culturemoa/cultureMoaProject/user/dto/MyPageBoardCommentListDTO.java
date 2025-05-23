package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 마이페이지에 사용할 사용자가 작성한 게시판 게시글과 댓글을 담은 DTO
 * myBoardLists : 조회한 게시판 리스트
 * myCommentLists : 조회한 댓글 리스트
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageBoardCommentListDTO {
    private List<MyPageBoardDTO> myBoardLists;
    private List<MyPageCommentDTO> myCommentLists;
}
