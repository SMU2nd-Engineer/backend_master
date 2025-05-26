package com.culturemoa.cultureMoaProject.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentsCommentDeleteInfoDTO {

    // 게시글 댓글 정보
    private Long idx;
    // 삭제되는 날짜를 입력하기 위해서 사용
    @DateTimeFormat(pattern = "MM/dd")
    private LocalDateTime edate; // 날짜 포맷된 sdate

}
