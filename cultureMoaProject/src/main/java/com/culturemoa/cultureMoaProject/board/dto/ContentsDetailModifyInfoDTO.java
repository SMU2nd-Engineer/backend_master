package com.culturemoa.cultureMoaProject.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentsDetailModifyInfoDTO {
    private Long idx;
    private Long user_idx;
    private Long category_idx;
    private String title;
    //    private int division;
    private String content;
//    private LocalDateTime sdate;
    private LocalDateTime cdate;
//    private LocalDateTime edate;

    //
    private Long id;
}
