package com.culturemoa.cultureMoaProject.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentsDTO {
    private Long idx;
    private Long user_idx;
    private Long category_idx;
    private String title;
//    private int division;
    private String content;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime sdate; // 날짜 포맷된 sdate
    private LocalDateTime edate;

}
