package com.culturemoa.cultureMoaProject.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentsCommentInfoDTO {

    // 게시판 댓글 정보
    private Long user_idx;
    private Long contents_idx;
    private Long comment_idx; // cotentsMapper의 게시글 상세페이지 댓글의 고유 idx 불러오기 별칭명과 동일하게 설정
    private String text;
//    @DateTimeFormat(pattern = "MM/dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime sdate; // 날짜 포맷된 sdate

    // 불러올 User 정보(작성자-회원 닉네임)
    private String nickname;


}
