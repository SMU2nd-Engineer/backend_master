package com.culturemoa.cultureMoaProject.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentsCommentInfoDTO {

    // 게시판 댓글 정보
    private Long contents_idx;
    private String text;

}
