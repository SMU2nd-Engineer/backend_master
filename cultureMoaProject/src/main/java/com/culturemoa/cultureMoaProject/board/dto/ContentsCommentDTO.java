package com.culturemoa.cultureMoaProject.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentsCommentDTO {
    private Long idx;
    private Long user_idx;
    private Long contents_idx;
    private String text;
    private LocalDateTime sdate;

}
