package com.culturemoa.cultureMoaProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 마이페이지 게시판 댓글 가져올 DTO
 * comment : 댓글 내용
 * date : 코멘트 작성 날짜
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageCommentDTO {
    private String comment;
    private LocalDateTime date;
}
