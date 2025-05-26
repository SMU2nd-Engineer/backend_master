package com.culturemoa.cultureMoaProject.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 마이페이지 게시판 댓글 가져올 DTO
 * comment : 댓글 내용
 * date : 코멘트 작성 날짜
 * contentIdx : 게시글 이동을 위한 idx
 * contentTitle : 게시글 제목
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageCommentDTO {
    private int idx;
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy/MM/dd") // 날짜 보내는 형식 지정
    private LocalDateTime sDate;
    private int contentIdx;
    private String contentTitle;
}
